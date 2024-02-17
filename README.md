# Deadline Tracker App

![](/img/main-screen.png)

## Background
The Deadline Tracker App is designed to help students prioritize their time and energy, allowing them to focus on their most important objectives. It addresses the common issue of students lacking proper time management and organization skills, resulting in incomplete assignments and lower grades. The app aims to enhance students' time management skills by providing features such as a To-Do list and a calendar view, motivating them to work on tasks earlier and reducing stress.

## Function
The Deadline Tracker App offers the following functionalities:

1. **To-Do List and Countdown**: The app displays all upcoming tasks in a list format, with the most urgent deadline highlighted. A day countdown timer is shown to remind users of the number of days left until the nearest deadline.

    ![](/img/todo-list.png)

2. **Add New Task**: Users can easily add new tasks to their list by entering the task name and its deadline.

    ![](/img/add-new-task.png)

3. **Calendar View**: The app provides a calendar view option, allowing users to visualize their upcoming tasks for a specific month.

    ![](/img/calendar.png)

4. **Task Completion**: Users can mark tasks as completed by checking the corresponding checkbox. Completed tasks are moved to the bottom of the list upon refresh.

5. **Task Deletion**: Tasks can be deleted by swiping the corresponding card to the left. A confirmation alert is displayed if the task is not yet completed.

6. **User Authentication**: The app allows users to create an account and log in to access their task list. Account information is securely stored in a cloud database.

    ![](/img/login.png)

7. **Firebase Realtime Database**: The Firebase Realtime Database is used to store and synchronize the task lists across devices. This allows users to access their task list from multiple devices and ensures that their data is always up to date.

    ![](/img/database.png)

## Design
The design of this app focuses on simplicity and user-friendliness. The main screen displays the most urgent deadline and a day countdown timer. The task list is presented in a card format, with each card containing a checkbox, task name, deadline, and an importance indicator. Users can easily add new tasks using the floating action button. The app also offers a calendar view option for a better overview of upcoming tasks. 

![chill](/img/chill.png)

## Testing
The Deadline Tracker App has undergone comprehensive testing to ensure its functionality and usability. The testing process includes:

**Local Unit Test**: This test verifies the creation of a new account and successful login.

**Instrumentation Test and UI Test**: These tests validate the user interface components, including account creation and login screens, as well as the main activity screen.

**Manual User Test**: A manual user test was conducted to evaluate the app's performance, usability, and user satisfaction.

## Conclusion
The Deadline Tracker App provides students with an effective tool for managing their deadlines and improving their time management skills. It offers a user-friendly interface, a variety of features, and secure data storage. While the app has been tested extensively, there may be areas for improvement in future updates.

## References
[Recycler View](https://developer.android.com/guide/topics/ui/layout/recyclerview)<br>
[Recycler View and adapter](https://developer.android.com/guide/topics/ui/layout/recyclerview?gclid=CjwKCAiAtdGNBhAmEiwAWxGcUm1rWEPiWpKRCD9Q8UYHYFMzczEQgVSDgJpbTok5XqWuGDSU0sGJKxoCgKcQAvD_BwE&gclsrc=aw.ds)<br>
[Recycler View and adapter](https://www.youtube.com/watch?v=__OMnFR-wZU)

[Compact Calendar View](https://www.youtube.com/redirect?event=video_description&redir_token=QUFFLUhqbl9Lel9TSkE5VzJUbXRMMXpVXzJ0bFZ0SzlHQXxBQ3Jtc0tsUEJuYV91UDkyLUpFQ1ZRQ1p5NHMzRlZxaHNWUFRMakVoS0M1anFxNjg5YU5zS2J4d3VCbXhaU3J0YTUwaTNzZHVCYXQ0X25JQlZZWDVJcjN1dFBZWWwtcUdzRHlrcWwySUpOd2Q3RUpzaXkzaGg4QQ&q=https%3A%2F%2Fgithub.com%2FSundeepK%2F)<br>
[Compact Calendar View](https://www.youtube.com/watch?v=xs5406vApTo)

[To-Do list and card view ](https://www.youtube.com/watch?v=HLM2_Pd2qE4)<br>
[To-Do list and card view ](https://www.youtube.com/watch?v=K7e1t5Oci0E&list=PLzEWSvaHx_Z2MeyGNQeUCEktmnJBp8136&index=6)
 
[SQLite Database handler](https://www.youtube.com/watch?v=e3fLWNEBPM0&list=PLzEWSvaHx_Z2MeyGNQeUCEktmnJBp8136&index=4)

[Firebase installation](https://firebase.google.com/docs/android/setup)<br>
[Firebase](https://www.youtube.com/watch?v=dRYnm_k3w1w)<br>
[Firebase Realtime database ](https://www.youtube.com/watch?v=lnidtzL71ZA)

[Guava for password security](https://github.com/google/guava)

[Local Unit Test](https://developer.android.com/training/testing/set-up-project)<br>
[Local Unit Test](https://stackoverflow.com/questions/51169020/how-to-call-appcompatactivity-oncreate-inunit-test-using-mockito)<br>
[Local Unit Test](http://junit.sourceforge.net/doc/faq/faq.htm#tests_16)

[UI testing](https://developer.android.com/training/testing/ui-testing/espresso-testing#java)