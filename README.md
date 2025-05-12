# I. Project Overview
**Price Comparator** is a Java Spring Boot application that provides a simulation for a real-world product comparation system for mutliple stores.
The main features of this project are:
* parse and load products and discounts from CSV files
* REST API for price comparison, optimization and price tracking
* extendable and clean arhitecture

The application is organized into clean layers, focusing on specific functionalities:
- **Controller layers**: handels HTTP requests and maps them to services
- **Service layers**:
  - contains both **interfaces** and **implementations**
  - includes a dedicated **data service** for reading and loading CSV data
- **Model package**:
  - contains **entities** representing the domain ('Product','Discount')
  - defines **requests and response DTOs** for the API
- **Util package**: provides helper utilities ('CsvLoader' for file parsing)

 CSV files ('products','discounts') are stored under: src/main/resources/csv

# II. Build and Run Instructions
What is needed before : Java 17+, Maven.
### Step 1 - Clone the repository: 
```bash
git clone https://github.com/andy12-star/price-comparator-backend.git
cd price-comparator-backend
```
### Step 2 - Build the project: 
```bash
mvn clean install
```
### Step 3 - Run the application: 
```bash
mvn spring-boot:run
```
# III. Simplifications
* There is no database - the application uses only CSV files
* Price History uses CSV snapshots to simulate price trends
* Price Alerts provide are checked manually via API
  
# IV. How to use implemented features
## Endpoints for Product
| Method | Endpoint | Description |
|-------|----------|-------------|
| GET   | /products  | Returns all products |
|GET| /products/store/{store}|Returns products by store|
|GET|/products/find?name={name}|Search products by name|
|GET|/products/recommendations?category={category}&brand={brand}| Suggests best products by unit price|
|GET|/products/{productId}/history|Shows price history|

## Endpoints for Discount
| Method | Endpoint | Description |
|-------|----------|-------------|
| GET   | /discounts  | Returns all discounts |
|GET| /discounts/store/{store}|Returns discounts by store|
|GET|/discounts/best|Returns best discounts|
|GET|/discounts/new| Returns newly discounts(last 3 days)|

## Endpoints for Basket Optimization
| Method | Endpoint | Description |
|-------|----------|-------------|
| POST   | /basket/optimize  | Returns all discounts |
### Example request body:
```json
{
  "productIds": ["P001", "P002", "P005"]
}
```
## Endpoints for Price Alerts
| Method | Endpoint | Description |
|-------|----------|-------------|
| POST   | /alerts  | Set a price alert for a product |
|GET| /alerts/check |Check which products have dropped below the target price|
### Example request body:
```json
{
  "productId":"P001", "targetPrice": 6.05
}
```
