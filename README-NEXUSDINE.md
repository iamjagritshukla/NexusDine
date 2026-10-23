# NexusDine — Smart Campus Canteen Management System

NexusDine is a web-based Smart Campus Canteen Management System designed to simplify canteen operations for students, administrators, and kitchen staff. It provides digital menu management, order tracking, inventory management, offers, reports, staff access, and QR-based customer access through a centralized application.

## Project Links

- **Live Website:** https://nexusdine.onrender.com/
- **Login Page:** https://nexusdine.onrender.com/login
- **GitHub Repository:** https://github.com/iamjagritshukla/NexusDine

## Default Login Credentials

Use the following credentials to access the application, provided these accounts remain configured on the deployed server.

| Role | Username | Password |
|---|---|---|
| Admin | `admin` | `admin123-change-this` |
| Cook | `cook` | `cook123-change-this` |

**Security note:** These are demonstration credentials. Change the passwords before using the system in a real environment. Do not publish production passwords, database credentials, API keys, or other secrets in a public repository.

## Key Features

### 1. Customer Access
- Access the canteen application through the live website.
- View the available canteen menu.
- Use the customer-facing pages to interact with the canteen system.
- Access the website through a QR code where configured.

### 2. Admin Dashboard
- Centralized dashboard for canteen administration.
- Overview of the canteen system.
- Order management.
- Menu and stock management.
- Offers management.
- Reports.
- Staff access management.
- Cook workspace access.
- Customer QR code page.

### 3. Order Management
- Manage customer orders through the administrative interface.
- Monitor orders using the available order-management workflow.

### 4. Menu and Inventory Management
- Manage menu items through the available menu interface.
- Manage stock through the menu and stock section.

### 5. Offers Management
- Manage canteen offers using the offers section.

### 6. Reports
- Access the reports section for available canteen reporting features.

### 7. Staff Access
- Provide separate access for authorized staff roles.
- Support administrator and cook access according to the configured application permissions.

### 8. Cook Workspace
- Provide a dedicated workspace for kitchen staff.
- Support the kitchen workflow available in the application.

### 9. QR-Based Access
- Provide a customer QR code through the administrative interface.
- Configure the public application URL using `APP_BASE_URL`.
- Use the deployed website address rather than a localhost address when generating a production QR code.

*The exact behavior of individual features depends on the implementation and configuration in the repository.*

## Technology Stack

The project uses the following technologies and services:

- **Programming Language:** Java
- **Backend Framework:** Spring Boot
- **Frontend:** HTML, CSS, JavaScript, Thymeleaf templates
- **Database Access:** Spring Data JPA / Hibernate
- **Database:** MySQL-compatible TiDB Cloud configuration
- **Build Tool:** Maven
- **Development IDE:** IntelliJ IDEA
- **Version Control:** Git and GitHub
- **Deployment Platform:** Render

## Application URLs

| Purpose | URL |
|---|---|
| Customer-facing website | https://nexusdine.onrender.com/ |
| Login page | https://nexusdine.onrender.com/login |
| GitHub source code | https://github.com/iamjagritshukla/NexusDine |

If a route is unavailable, verify the application's controller mappings and deployed configuration.

## Getting Started

Follow these steps to run the project locally.

### Prerequisites

Install the following software:

1. Java Development Kit (JDK), compatible with the project's configured Java version.
2. IntelliJ IDEA or another Java IDE.
3. Git.
4. Maven, or use the Maven Wrapper included in the repository.
5. Access to a compatible configured database.

### Step 1: Clone the Repository

Open PowerShell or a terminal and run:

```bash
git clone https://github.com/iamjagritshukla/NexusDine.git
```

Move into the cloned project directory:

```bash
cd NexusDine
```

If the Spring Boot application is inside a nested folder, open the directory containing the application's `pom.xml` before running Maven.

### Step 2: Open the Project

1. Open IntelliJ IDEA.
2. Select **Open**.
3. Choose the project directory containing `pom.xml`.
4. Allow IntelliJ IDEA to load the Maven project.
5. Configure a compatible JDK in Project Structure.
6. Wait for the dependencies to finish downloading.

### Step 3: Configure Environment Variables

Configure the database connection and public application URL using environment variables or the configuration mechanism supported by the project.

Example variable names:

```properties
APP_BASE_URL=https://nexusdine.onrender.com
DB_URL=your_database_connection_url
DB_USERNAME=your_database_username
DB_PASSWORD=your_database_password
```

These are example values and must be replaced with the appropriate configuration for your environment.

**Important:**
- Never commit real database credentials to GitHub.
- Do not put passwords or API keys directly into publicly shared source files.
- Use your own database connection details when running locally.
- Ensure the database is reachable from the machine or hosting platform running the application.

### Step 4: Run the Application

From the directory containing `pom.xml`, run the Maven Wrapper in PowerShell:

```powershell
.\mvnw.cmd spring-boot:run
```

On macOS or Linux, use:

```bash
./mvnw spring-boot:run
```

Alternatively, open the Spring Boot main application class in IntelliJ IDEA and run it.

### Step 5: Open the Local Website

If the application starts on port `8080`, open:

http://localhost:8080/

The login route, if configured as shown above, is:

http://localhost:8080/login

The local port may differ depending on your application configuration.

## Deployment on Render

NexusDine is deployed at:

https://nexusdine.onrender.com/

To configure a deployment, use the following general procedure:

1. Push the project source code to GitHub.
2. Connect the repository to Render.
3. Configure the Java/Maven build and start commands according to the repository structure.
4. Add the required environment variables in Render.
5. Configure the database connection and ensure the hosting service can reach the database.
6. Set `APP_BASE_URL` to the public application URL.
7. Deploy the application and inspect the deployment logs.
8. Open the live website and test the relevant pages and login workflows.

### Environment Variables

| Variable | Purpose |
|---|---|
| `APP_BASE_URL` | Public base URL used for production links and QR codes |
| `DB_URL` | Database connection URL |
| `DB_USERNAME` | Database username |
| `DB_PASSWORD` | Database password |

These names must match the application's actual configuration. Confirm the configuration files if the application uses different environment-variable names.

## QR Code Configuration

For a production QR code, configure:

```properties
APP_BASE_URL=https://nexusdine.onrender.com
```

The QR code should point to the intended public application URL.

Before distributing the QR code:

1. Open the QR code page from the admin dashboard.
2. Scan the QR code using a phone.
3. Verify that the scanned URL is exactly the intended website address.
4. Confirm that no extra text, spaces, or copied button labels appear in the URL.
5. Test the destination using mobile data as well as Wi-Fi.

If the QR code opens an invalid address, inspect the QR page's template and JavaScript to identify how the QR destination is constructed.

## User Guide

### Admin
1. Open the application.
2. Sign in with an authorized administrator account.
3. Use the dashboard to access Overview, Orders, Menu & Stock, Offers, Reports, Staff Access, Cook Workspace, and Print Customer QR.
4. Use the relevant section to perform the supported administrative operations.

### Cook
1. Open the application.
2. Sign in using an authorized cook account.
3. Open the Cook Workspace.
4. Follow the order or kitchen workflow available to the cook role.

### Customer
1. Open the live website or scan the configured customer QR code.
2. Navigate the available customer-facing pages.
3. Follow the order workflow implemented by the application.

## Project Structure

The precise folder names may differ depending on the current repository version. A typical Spring Boot structure looks like this:

```text
NexusDine/
├── src/
│   └── main/
│       ├── java/
│       │   └── ... application source code
│       └── resources/
│           ├── templates/       # Thymeleaf HTML templates
│           ├── static/          # CSS, JavaScript and static assets
│           └── application.properties
├── mvnw
├── mvnw.cmd
├── pom.xml
└── README-NEXUSDINE.md
```

This is an illustrative structure. Refer to the actual repository for the authoritative file and package names.

## Testing Checklist

After starting the application locally or deploying an update, verify:

- [ ] The application starts without fatal errors.
- [ ] The home page loads.
- [ ] The login page loads.
- [ ] Authorized administrator login works.
- [ ] Authorized cook login works.
- [ ] Dashboard navigation works.
- [ ] Order management pages load.
- [ ] Menu and stock pages load.
- [ ] Offers and reports pages load.
- [ ] Staff access behaves according to the configured permissions.
- [ ] The cook workspace loads.
- [ ] The QR code contains the correct public URL.
- [ ] Database operations work as expected.
- [ ] The deployed application works on a mobile browser.

## Troubleshooting

### Application Does Not Start
- Verify that the installed JDK is compatible with the project.
- Confirm that Maven dependencies download successfully.
- Review the terminal output for compilation or startup errors.
- Check the configured server port.

### Database Connection Failure
- Verify `DB_URL`, `DB_USERNAME`, and `DB_PASSWORD`.
- Confirm that the database service is running and reachable.
- Check database permissions and network access.
- Review the application logs for the underlying error.

### Login Failure
- Confirm that the account exists and is enabled in the configured environment.
- Verify the credentials and the role permissions.
- Check whether the deployed database contains the expected application data.

### QR Code Opens an Invalid URL
- Check the `APP_BASE_URL` environment variable.
- Inspect the QR code template and its JavaScript.
- Scan the generated QR code and verify the complete decoded URL.
- Confirm that the QR code contains only the destination URL.

### Deployment Failure
- Inspect the Render deployment logs.
- Verify the build and start commands.
- Confirm that all required environment variables are configured.
- Check database connectivity from the deployed service.

## Security Notes

- Replace demonstration passwords before real-world use.
- Use secure, role-appropriate authentication and authorization.
- Keep production secrets out of source code and public documentation.
- Use HTTPS for the deployed application.
- Review access controls before making the application available to real users.

## Future Improvements

Potential enhancements, depending on project requirements, include:

- Online payments.
- Real-time order status updates.
- Improved inventory alerts.
- More detailed sales and performance analytics.
- Customer order history.
- Mobile-responsive refinements.
- Automated testing and stronger security controls.

These are possible future improvements and should not be considered implemented features unless they are present in the source code.

## Author and Repository

**Project:** NexusDine — Smart Campus Canteen Management System

**GitHub Repository:** https://github.com/iamjagritshukla/NexusDine

**Live Application:** https://nexusdine.onrender.com/

The GitHub repository is the source for the project's code and related files. Refer to the repository for the current implementation and project updates.
