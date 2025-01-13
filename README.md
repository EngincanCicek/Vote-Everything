# Vote Everything

Vote Everything provides a platform where users can anonymously vote on any topic, person, or concept. Users can view and comment on existing polls or create new ones based on their preferences. The platform aims to deliver a transparent and user-friendly experience.

---

## Features

### SplashActivity
This screen checks the user's session status when the app is launched. If a session exists, the user is directed to `MainActivity`; otherwise, they are redirected to `SignInActivity`. The transition occurs with a one-second delay.

### SignInActivity
- Uses Firebase Authentication for email and password verification.
- Separates business logic and UI using ViewModel.
- Redirects users to the main screen upon successful login.

### RegisterActivity
- Allows users to create accounts by providing an email and password.
- Validates user inputs (e.g., email format and password length).
- Redirects users to the main screen upon successful registration.

### MainActivity - Home
- Provides navigation for the main screen.
- Displays posts using `RecyclerView` and `PostAdapter`.
- Users can filter posts by title through the search bar.


### MainActivity - Profile
- Manages user sessions and data.
- Dynamically loads user posts and other information.
- Navigation allows switching between Home and Profile fragments.

### CreatePostActivity
- Enables users to create new posts.
- Adds rating options and checks for post uniqueness.
- Notifies users upon successful post creation and saves data to Firestore.

### ShowPostActivity
- Displays detailed information about posts and user comments.
- Lists comments using `RecyclerView` and `CommentAdapter`.
- Allows users to add comments and provides editing options for post owners.

---

## Firebase Structure

- **Users Collection**: Stores user information (userId, username, postIdList, commentIdList) and links to posts/comments.
- **Posts Collection**: Stores post information (postId, description, userId) and includes a list of comments.

