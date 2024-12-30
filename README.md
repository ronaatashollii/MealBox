# MealBox Android Application

MealBox is an Android application designed to streamline the food ordering process. The app provides users with a convenient way to browse the menu, add items to their cart, and place orders. It also features secure user authentication and integrates with an SQLite database for managing data such as user information, orders, and cart details.

#Features

- User Authentication: Includes login, signup, and forgot password functionality.

- SQLite Database Integration: Manages users, orders, and cart details using a local SQLite database.

- Menu Browsing: Allows users to browse food items and view details.

- Cart Management: Supports adding, removing, and updating items in the cart.

- Order Processing: Enables users to review and confirm orders.

- Contact Page: Provides a way for users to contact support or view app information.

- Email Integration: Sends verification codes and password recovery emails using a custom EmailSender class.

  # Project Structure

# Key Classes

- DBHelper: Manages database creation, versioning, and CRUD operations.

- MainActivity: Entry point of the application, handling user authentication.

- HomePage: Displays the menu and allows users to navigate the app.

- CartActivity: Manages cart operations like adding and removing items.

- CartManager: Utility class for handling cart-related logic.

- OrderActivity: Processes and finalizes user orders.

- ForgotPasswordActivity: Allows users to reset their password.

- SignUpActivity: Facilitates new user registration.

- EmailSender: Handles email operations for verification and password recovery.

# Database Tables

- users: Stores user details such as name, email, phone, and password.

- orders: Stores order details including order ID, product count, and total amount.

- products: Stores product items available.

# How to Run the Project

- Clone the repository:

- git clone <repository-url>

- Open the project in Android Studio.

- Build the project and ensure all dependencies are installed.

- Run the app on an emulator or physical device (API level 21 or higher).
