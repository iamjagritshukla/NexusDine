# NexusDine - QR Canteen Ordering

## Included in this project
- Customer menu, search/category filters, cart, checkout and receipt.
- Real QR code page at `/qr` pointing to the customer menu.
- Offline payment works and stays `PENDING` until collected. Online payment is displayed as disabled/coming soon.
- Role-protected Admin Management dashboard at `/admin` with order overview, menu CRUD, inventory quantities, low-stock warnings, per-item offers and CSV export.
- Role-protected Cook dashboard at `/cook` with order status updates and read-only stock visibility.
- Role-separated staff login at `/login`; Admin and Cook use separate roles and routes.
- Cook statuses: `PLACED -> PREPARING -> READY -> COMPLETED`; status polling every 4 seconds.
- Menu demo items are seeded automatically when the database is empty.

## 1. Configure MySQL
Start MySQL Server and run once in MySQL Workbench:

```sql
CREATE DATABASE nexusdine_db;
```

The database password is read from the `DB_PASSWORD` environment variable. Do not put real credentials into source files or commit them. You can also set `DB_USERNAME` and `DB_URL`.

## 2. Configure IntelliJ
- Open the `digitalcanteen` folder.
- Set Project SDK and Maven runner JDK to Java 25.
- Reload Maven.

## 3. Run
Windows PowerShell, from the project root:

```powershell
$env:DB_USERNAME="root"
$env:DB_PASSWORD="<your-local-MySQL-password>"
$env:APP_ADMIN_USERNAME="admin"
$env:APP_ADMIN_PASSWORD="<choose-a-strong-admin-password>"
$env:APP_COOK_USERNAME="cook"
$env:APP_COOK_PASSWORD="<choose-a-strong-cook-password>"
$env:APP_BASE_URL="http://localhost:8080"
.\mvnw.cmd spring-boot:run
```

If your local MySQL root password is empty, set `DB_PASSWORD` to an empty string or remove that line. Do not share real database credentials.

## 4. Open pages
- Customer menu: http://localhost:8080/
- Customer tracking: `/orders/{id}/track` (link appears after checkout)
- Staff login: http://localhost:8080/login
- Admin dashboard (after Admin login): http://localhost:8080/admin
- Cook dashboard (after Cook login): http://localhost:8080/cook
- Print/display customer QR (Admin login required): http://localhost:8080/qr
- Health: http://localhost:8080/actuator/health

## 5. Test the customer-to-cook flow
1. Open the customer menu and add a few items.
2. Enter a valid 10-digit Indian mobile number.
3. Select offline payment and place the order.
4. Open the bill from the confirmation modal.
5. Open `/cook` in another tab; the same token should appear within 4 seconds.
6. Click Start preparing, Mark ready, then Complete pickup.
7. Refresh the receipt/customer tracking via order API to see the current order status.

## QR on a real phone
`localhost` only works on the same computer. To scan from a phone on the same Wi-Fi, set `APP_BASE_URL` to the computer's LAN URL, e.g. `http://192.168.1.10:8080`, and allow port 8080 through Windows Firewall. The phone and computer must be on the same network. For public access, use a deployed HTTPS URL.

## Security and inventory notes
- Configure non-default `APP_ADMIN_PASSWORD` and `APP_COOK_PASSWORD` before public deployment. Demo fallback credentials are `admin` / `admin123-change-this` and `cook` / `cook123-change-this`; change them immediately.
- Admin-only APIs manage menu items, stock, discounts and reports. Cook APIs expose order workflow and read-only stock only.
- Inventory currently tracks stock units per menu item; recipe-level ingredient deductions are a possible next iteration.
- Customer QR always targets the public customer menu root, never a staff dashboard. For any phone on the internet, deploy the app and set `APP_BASE_URL` to its public HTTPS URL.
- MySQL persistence is required; the application does not silently switch to an in-memory database.


## Updated management roles
- Login: `/login`
- Admin: `/admin` (orders/revenue summary, menu add/edit/remove, stock thresholds, availability, offers/discounts, CSV export, QR print page, and Cook workspace access).
- Cook: `/cook` (order status updates plus read-only menu-stock/low-stock view).
- Customer QR always encodes the configured `APP_BASE_URL` plus `/`, which is the public customer menu route.
- Stock is tracked as sellable units for each menu item. Ingredient recipe/BOM deductions are not implemented in this version.
- The Admin/Cook accounts are environment-configured in-memory accounts. Change demo fallback passwords before any public deployment.
