# Assignment 4: Restaurant Kitchen Subsystem Facade

A Java implementation demonstrating the **Facade Pattern** orchestrating a 3-stage chain (`Subsystem A → Subsystem B → Subsystem C`) with durable file-based persistence, 2-depth rollback compensations, F.I.R.S.T. unit tests, and a live CLI demo.

---

## Domain Overview: Restaurant Kitchen

The application coordinates three independent subsystems representing a restaurant kitchen pipeline:
1. **Subsystem A (Intake & Reservation):** Manages order placement and ingredient reservation (`OrderQueue`, `InventoryChecker`).
2. **Subsystem B (Processing & Execution):** Allocates cooking stations and enforces quality checks (`StationAllocator`, `QualityChecker`).
3. **Subsystem C (Finalization & Confirmation):** Generates billing transactions and dispatches couriers (`BillingModule`, `DeliveryDispatcher`).

---

## Principle of Least Knowledge (Law of Demeter)

### Before Facade (High Coupling)
Without a facade, the client must directly interact with multiple classes across three distinct subsystems, maintaining complex workflow knowledge, persistence management, and error handling/rollbacks:

```java
// Client directly manages Subsystem A
InventoryChecker inventory = new InventoryChecker();
OrderRepository orderRepo = new OrderRepository();
Order order = inventory.reserveIngredients("ORD-101", "Steak");
orderRepo.save(order);

// Client directly manages Subsystem B & must handle partial failure
StationAllocator allocator = new StationAllocator();
StationRepository stationRepo = new StationRepository();
try {
    allocator.allocateStation("ORD-101");
} catch (Exception e) {
    // Client has to manually handle rollback for Subsystem A
    inventory.releaseIngredients("ORD-101");
    order.setStatus(OrderStatus.CANCELLED);
    orderRepo.save(order);
    return;
}

// Client directly manages Subsystem C & multi-step rollback
BillingModule billing = new BillingModule();
try {
    billing.processBillingAndDispatch("ORD-101");
} catch (Exception e) {
    // Client manually rolls back B, then A
    allocator.releaseStation("ORD-101");
    inventory.releaseIngredients("ORD-101");
    order.setStatus(OrderStatus.CANCELLED);
    orderRepo.save(order);
}
```

### After Facade (Loose Coupling)
With `KitchenFacade`, the client only interacts with one immediate friend (`KitchenFacade`), providing a single high-level method:

```java
KitchenFacade facade = new KitchenFacade();

// One call handles the entire chain, persistence, and complex rollbacks
boolean success = facade.executeOrderFlow("ORD-101", "Steak");
```

---

## Written Reflection: Facade vs. Adapter

### Intent Analysis
While both patterns act as wrappers around existing components, they address fundamentally different structural design challenges:

* **Adapter Pattern:** Translates an existing interface into another interface expected by a client. Its core intent is **interface compatibility**—making two incompatible interfaces work together without altering their underlying business logic.
* **Facade Pattern:** Provides a unified, high-level interface over a complex collection of subsystems. Its core intent is **simplification, orchestration, and decoupling**.

### Why This Problem Calls for a Facade
In our Restaurant Kitchen application, Subsystems A, B, and C are not incompatible interfaces that need to match an existing interface signature. Rather, they are three distinct, fully functional domain modules that must execute in a sequential pipeline (`Intake → Cooking → Delivery`).

1. **Subsystem Orchestration:** The client needs a single entry point to execute a multi-step saga (`A → B → C`), rather than translating individual method signatures.
2. **State Coordination & Compensation:** A single operation requires state persistence across multiple steps and complex two-stage transactional unwinding (rolling back `B` then `A` upon a `C` failure). An Adapter merely converts call signatures, whereas a Facade encapsulates sequencing, conditional checks, and rollback coordination logic.

Therefore, **Facade** is the correct structural pattern for this system.

---

## How to Build and Run

### Prerequisites
* Java 17 or higher
* Maven / Gradle

### Running the Demo Application
Build and run the project using Maven:
```bash
mvn clean compile exec:java -Dexec.mainClass="com.restaurant.kitchen.Main"
```

### Resetting Persisted State
To reset the durable file-based persistence store to a clean slate before a fresh run:
```bash
# Remove persisted binary/JSON state files
rm -f data_order.dat data_station.dat data_dispatch.dat
```
*(Alternatively, select the "Reset System State" option in the CLI menu).*

---

## Running Tests & Coverage

To run all F.I.R.S.T.-compliant unit tests (including two-depth compensation tests and persistence reload tests):
```bash
mvn clean test
```

To generate and view the JaCoCo test coverage report:
```bash
mvn jacoco:report
```
The generated report will be available under `target/site/jacoco/index.html`.
