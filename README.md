# PSPsystem1

## 📖 Project Description

This project is a simplified implementation of a Payment Service Provider (PSP system).  
It accepts payment data, determines which acquirer to route the transaction to based on BIN rules, simulates the acquirer's response, and returns the result to the merchant.

---

## 🏗️ Architecture and Design

**Layers:**
- `controller` → REST API (`PaymentController`)
- `service` → business logic (`PaymentService`, `AcquirerService`)
- `model` → DTOs and entities (`PaymentRequest`, `PaymentResponse`, `Transaction`)
- `validation` → custom validator `@ValidCardNumber`

**Interfaces and Dependency Injection:**
- `PaymentService` and `AcquirerService` are defined as interfaces.
- Implementations (`PaymentServiceImpl`, `AcquirerServiceMockImpl`) are injected into controllers via constructor injection.
- This allows easy replacement of implementations.

**Validation:**
- Uses JSR-380 (Bean Validation) + custom annotations.
- Validation errors are automatically handled by Spring Boot and returned as **400 Bad Request** with error details.

**UUID for transactionId:**
- Each transaction receives a unique identifier (`UUID.randomUUID().toString()`).
- This avoids collisions and makes the system thread-safe.

**Logging:**
- Uses SLF4J + Logback.
- Logs key events: transaction creation, acquirer selection, processing result.
- Sensitive data (CVV, full card number) is never logged.

---

## 🚀 Running the Project

### Prerequisites
- Java 21
- Maven 3.8+ or IntelliJ IDEA (with Maven support)

### Build and Run
```bash
mvn clean package
java -jar target/PSPsystem1-0.0.1-SNAPSHOT.jar
```
or directly from IDE: run `PspApplication.java`

## API Documentation

### 🔗 POST /payments

#### Example request:
```json
{
  "cardNumber": "4242424242424242",
  "expiryDate": "12/25",
  "cvv": "123",
  "amount": 100.0,
  "currency": "USD",
  "merchantId": "M123"
}
```
#### Example response:
```json
{
  "transactionId": "98ac573d-e20b-467e-b954-6c21b6200b1a",
  "status": "Approved",
  "message": "Processed by Acquirer A"
}
```

## 🧪 Testing

Integration tests (`PaymentControllerIntegrationTest`) are implemented with MockMvc.  
The following scenarios are tested:

- ✅ **Successful transaction (Approved)**  
- ❌ **Denied transaction (Denied)**  
- ⚠️ **Missing card number**  
- ⚠️ **Invalid card number (Luhn check failed)**  
- ⚠️ **Invalid ExpiryDate**  
- ⚠️ **Invalid CVV**  
- ⚠️ **Invalid Amount**  
- ⚠️ **Invalid Currency**  
- ⚠️ **Invalid MerchantId**  

## 🔒 Security

- CVV and full card number are never logged or returned.
- In a real system, card data must be stored and transmitted only in encrypted form.
- TLS is strongly recommended for all requests.