# ConditionWatcher

Simple class created in order to make Android automation testing easier, faster, cleaner and more intuitive. It synchronizes operations that might occur on any thread - with test thread. ConditionWatcher can be used as a replacement to Espresso's IdlingResources or it can work in parallel with them.

When we started our adventure with Android Espresso, we came across with various problems connected to IdlingResources. Before we were able to understand how Espresso works on lower layer and explain behaviour of IdlingResources, we created our own tool beforehand and based tests on it. As we can see now principle of operation is very similar, yet we would like present to you perks of ConditionWatcher as they might become useful to you.

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

        DialogFragment f = 
            (DialogFragment) activity.getFragmentManager().findFragmentByTag(LoadingDialog.TAG);
        return f == null;
    }
}
```

Full code can be found in provided sample project. It also contains the same test created with usage of IdlingResources with exactly the same logic to pinpoint the differences.

## Why ConditionWatcher?

##### - It is fast! - save time on synchronization
You are provided with `setWatchInterval()` method which allows you to change interval with which `ConditionWatcher` checks if all conditions are met and if test can continue. In comparison to `IdlingResource` if method `isIdleNow()` returns false, framework waits 5 seconds before it checks your condition again. It doesn't only limits your options but also forces test to wait without real need. 

##### - It is clean! - reduce number of lines in your code
`ConditionWatcher`'s wait `Instruction` objects are reusable. Consequently you don't need to unregister them before next usage. `IdlingResource` is added to `List` stored in  `IdlingResourceRegistry` class. That List can't store two objects with the same name. Furthermore once `IdlingResource` is idled, it becomes unusable until you unregister it and register again.

##### - Less restrictions! - you are the master of your own test
`IdlingResource` after registered within `IdlingResourceRegistry` starts to wait for app's idle state only after Espresso method was called within test. So you have to use either `onView()` or `onData()` to wait for idle. `ConditionWatcher` is not connected to Espresso framework. It uses simple `Thread.sleep()` method which makes your <b>test thread</b> to wait until condition is met. You can decide what to wait for, how and when.

##### - It is easy! - be creative, modify the way you like

`ConditionWatcher` consists of 51 lines of code. It is very easy to understand and you can adjust it to your needs. Each app is different after all. 

##### - It is intuitive! - there is no black box part

You don't have to be worried of unexpected behaviours. `ConditionWatcher` will stop waiting for your condition instantly after method `checkCondition()` returns true.  If you haven't noticed it yet, `IdlingResource`'s method `isIdleNow()` is still being called a few times (with ~1ms interval) after it returns true. 

## Download

### Java code

If you don't want to add another dependency to your project, just copy [ConditionWatcher.java](https://github.com/AzimoLabs/ConditionWatcher/blob/master/conditionwatcher/src/main/java/com/azimolabs/conditionwatcher/ConditionWatcher.java) and [Instruction.java](https://github.com/AzimoLabs/ConditionWatcher/blob/master/conditionwatcher/src/main/java/com/azimolabs/conditionwatcher/Instruction.java) classes to your source directory.

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

