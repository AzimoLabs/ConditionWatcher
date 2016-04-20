# ConditionWatcher

Simple class created in order to make Android automation testing easier, faster, cleaner and more intuitive. It synchronizes operations that might occur on any thread - with test thread. ConditionWatcher can be used as a replacement to Espresso's IdlingResources or it can work in pararell with them.

When we started our adventure with Android Espresso, we came across with various problems connected to IdlingResources. Before we were able to understand how Espresso works on lower layer and explain behaviour of IdlingResources, we created our own tool beforehand and based tests on it. As we can see now principle of operation is very similar, yet we would like present to you perks of ConditionWatcher as they might become useful to you.


## Why ConditionWatcher?

##### - It is fast! - save time on synchronization
You are provided with setWatchInterval() method which allows you to change interval with which ConditionWatcher checks if all conditions are met and if test can continue. In comparison to IdlingResource if method isIdleNow() returns false, framework waits 5 seconds before it checks your condition again. It doesn't only limits your options but also forces test to wait without real need. 

Example: Imagine you have test set of 500 tests. Your application's main screen has menu with cool animation that takes 600 milliseconds to reveal buttons. If you used IdlingResource to wait before your buttons are visible and clickable, you would waste around 4,4 seconds because first condition check of isIdleNow() will return false. Now imagine losing 4,4 seconds on most of 500 tests.

##### - It is clean! - reduce number of lines in your code
ConditionWatcher's wait Instructions are reusable. Consequently you don't need to unregister them before next usage. IdlingResources are added to List stored in  IdlingResourcePolicy class. That List can't store two objects with the same name. Furthermore once IdlingResource is idled, it becomes unusable until you unregister it and register again.

##### - Less restrictions! - you are the master of your own test
IdlingResource after registered within IdlingResourcePolicy starts to wait for app's idle state only after Espresso method was called within test. So you have to use either onView() or onData() or assertThat() to wait for idle. ConditionWatcher is not connected to Espresso framework. It uses simple Thread.sleep() method which makes your <b>test thread</b> to wait until condition is met. You can decide what to wait for, how and when.

##### - It is easy! - be creative, modify the way you like

ConditionWatcher consists of 51 lines of code. It is very easy to understand and you can adjust it to your needs. Each app is different after all. 

##### - It is intuitive! - there is no black box part

You don't have to be worried of unexpected behaviours. ConditionWatcher will stop waiting for your condition instantly after method checkCondition() returns true.  If you haven't noticed it yet, IdlingResource's method isIdleNow() is still being called a few times (with ~1ms interval) after it returns true. 

Example: Imagine you provide IdlingResource with a reference to button which is GONE and should appear after some condition is met. Button starts next activity. IdlingResource's isIdleNow() method returns (button != null && button.getVisibility() == View.VISIBLE). When Espresso detects that your button is already visible, it will perform click() on it. Next activity will start, but isIdle() now will be still called a few times. In next activity there is no button so condition (button != null && button.getVisibility() == View.VISIBLE) will change from true to false. Your test will freeze and you will receive error that click on button couldn't be performed - even though it happened. Confusing right?



## Usage

ConditionWatcher doesn't need any setup. After classes are added to your project you are ready to go. 

##### ConditionWatcher.java - performs wait
Singleton which is automatically created after call to any of it's methods. It is meant to be destroyed along with test process. To make test code wait, you need to call `waitForCondition(Instruction instruction)` method. ConditionWatcher will check if the expected state is achieved with `250ms interval` and throw `timeout exception after 60 seconds`. Those values `can be easly changed` and each wait case can be handled separately. 


##### Instruction.java - informs what to wait for
It provides ConditionWatcher with information what should be scanned and when conditions are met. ConditionWatcher will keep calling Instrucion's `checkCondition()` method with interval until it returns true. Furthermore Instruction contains `getDescription()` method where you can place your additional your own additional logs (for example dump of elements connected to your current wait). 

ConditionWatcher in test code looks like that:

```java
@Test
public void shouldDisplayServerDetails_conditionWatcher() throws Exception {
    List<Server> servers = DataProvider.generateServerList();
    Server thirdServer = servers.get(2);

    // SplashActivity
    ConditionWatcher.waitForCondition(new BtnStartAnimationInstruction());
    onView(withId(R.id.btnStart)).perform(click());

    // ListActivity
    ConditionWatcher.waitForCondition(new ServerListLoadingInstruction());
    onData(anything()).inAdapterView(withId(R.id.lvList)).atPosition(2).perform(click());

    // DetailsActivity
    ConditionWatcher.waitForCondition(new LoadingDialogInstruction());
    onView(withText(thirdServer.getName())).check(matches(isDisplayed()));
    onView(withText(thirdServer.getAddress())).check(matches(isDisplayed()));
    onView(withText(thirdServer.getPort())).check(matches(isDisplayed()));
}
    
```

Example of one instruction which waits until loading dialog disappear from view hierarchy:


```java
public class LoadingDialogInstruction extends Instruction {
    @Override
    public String getDescription() {
        return "Loading dialog shouldn't be in view hierarchy";
    }

    @Override
    public boolean checkCondition() {
        Activity activity = ((TestApplication)
                InstrumentationRegistry.getTargetContext().getApplicationContext()).getCurrentActivity();
        if (activity == null) return false;

        DialogFragment f = (DialogFragment) activity.getFragmentManager().findFragmentByTag(LoadingDialog.TAG);
        return f == null;
    }
}
```

Full code can be found in provided sample project. It also contains the same test created with usage of IdlingResources with exactly the same logic to pinpoint the differences.


## Download

### Java code

If you don't want to add another dependency to your project, just copy ConditionWatcher.java and Instruction.java classes to your source directory.

## License

    Copyright (C) 2016 Azimo

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.

