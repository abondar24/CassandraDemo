# CassandraDemo

Set of basic examples of Cassandra Usage

## Demos
1. Connection (cc) - Cassandra DB connection reading system data.
2. Connection Listener (clc) - Usage of connection listener.
3. Session (sc) - Connection Session to Cassandra.
4. Simple Statement (ssc) - Execute a simple CQL statement.
5. Prepared Statement (psc) - Execute a prepared CQL statement.
6. Query Builder (qbc) - Execute a statement built by query builder.
7. ResultSet Future Listener (rsfc) - State listener example.

### Notes
- Arguments to run are in ()
- Some demos require keyspaces creation. Run hotel.cql and reservations.cql before running demos.
## Build and run

```yaml
mvn clean install

java -jar <jar-location>/cassandra.jar  <demo-name>
```
