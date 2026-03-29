# Transactions, Deadlocks, and Batching

## Objectives
* **Understand ACID Properties:** Gain practical experience with the core principles of database transactions.
* **Identify Concurrency Issues:** Observe and mitigate common database concurrency anomalies, including:
  * Dirty Reads
  * Non-Repeatable Reads
  * Phantom Reads
  * Lost Updates
  * Deadlocks
* **Explore Isolation Levels:** Understand the trade-offs and protections offered by different database isolation levels.
* **Master Batch Processing:** Analyze the performance benefits of batching operations compared to single executions.

*Note: This project uses **MySQL** to effectively demonstrate and manage transaction isolation levels.*

---

## ACID Transactions

### Dirty Reads
**Scenario:** What value did Transaction B read?
Transaction B read the uncommitted value set by Transaction A. However, because Transaction A subsequently rolled back its changes, Transaction B was left holding a value that no longer existed in the database.

**Solution:** The `READ COMMITTED` isolation level prevents dirty reads by ensuring that only committed data can be read by other transactions.

<p align="center">
    <img src="assets/Dirty%20Reads.png" width="60%" height="60%" alt="Dirty Reads Demonstration">
</p>

### Non-Repeatable Reads
**Scenario:** Did the two readings return the same value?
No, the two `SELECT` statements returned different values within the same transaction. This occurred because another transaction updated the row and committed the change between the first and second reads.

**Solution:** The `REPEATABLE READ` isolation level prevents this by maintaining a read lock (or consistent snapshot) for the entire duration of the transaction.

<p align="center">
    <img src="assets/Unrepeatable%20Reads.png" width="60%" height="60%" alt="Non-Repeatable Reads Demonstration">
</p>

### Phantom Reads
**Scenario:** How do phantom reads occur?
A phantom read happens when a transaction queries a range of rows, and a concurrent transaction inserts a new row that falls into that range. When the first transaction repeats the query, it sees a "phantom" row that wasn't there initially.

**Solution:** The `SERIALIZABLE` isolation level prevents phantom reads by employing range locks (or table-level locks) for the entire duration of the transaction.

<p align="center">
    <img src="assets/Phantom%20Reads.png" width="60%" height="60%" alt="Phantom Reads Demonstration">
</p>

### Lost Updates
**Scenario:** What happens when two transactions update the same value?
When two transactions concurrently read a value and then attempt to update it based on that initial read, one update can overwrite the other. For example, if Transaction A adds 100 and Transaction B adds 200 to the same base value simultaneously, the final result might only reflect one of the updates instead of the combined 300.

<p align="center">
    <img src="assets/Lost%20Updates.png" width="60%" height="60%" alt="Lost Updates Demonstration">
</p>

---

## Deadlocks

This project implements a scenario to deliberately trigger a MySQL deadlock exception:
`MySQLTransactionRollbackException: Deadlock found when trying to get lock; try restarting transaction`.

Additionally, a corrected implementation is provided to demonstrate how enforcing a consistent order of operations across transactions can successfully prevent deadlocks.

<p align="center">
    <img src="assets/Deadlock%20Error.png" width="70%" height="70%" alt="Deadlock Error">
    <br>
    <img src="assets/Fixed%20Deadlock.png" width="60%" height="60%" alt="Fixed Deadlock Strategy">
</p>

---

## Batch Processing Performance

To evaluate performance differences, 5,000 rows were inserted into a table using three distinct strategies. The results represent the average execution time across four runs.

1. **Auto-Commit (After Every Insert):** ~9,110 ms
   * **Mechanism:** Requires 5,000 distinct network communications and 5,000 separate disk writes. Highly inefficient.

2. **Manual Commit (Every 100 Inserts):** ~649 ms
   * **Mechanism:** Reduces disk writes to 50, but still requires 5,000 network round-trips for each individual `pstmt.executeUpdate()`.

3. **Batching and Single Commit:** ~92 ms
   * **Mechanism:** Batches 50 inserts into a single `pstmt.executeBatch()` call, reducing network communications to just 100, followed by a single disk write at the end. **Most efficient.**

<p align="center">
    <img src="assets/Batch%20Processing.png" width="20%" height="20%" alt="Batch Processing Code Snippet">
    <img src="assets/Batch%20Results.png" width="72%" height="72%" alt="Batch Processing Results Chart">
</p>
