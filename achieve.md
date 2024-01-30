## 1. Partition
The data will be archived on a temporal basis. Our archival process will handle partitioning a given table by years, months or weeks.

Drawbacks: it will make INSERTs and UPDATEs a tiny bit slower.
## 2. Replica
Adding a replica for the archive (RA) and not allow the delete statements on the tables we want to archive to flow through the replication stream. The deletions must be executed with cron job on all the normal servers.
![](https://percona.com/blog/wp-content/uploads/2022/08/Add_Ra.png)
Drawbacks: A cron job or a SQL event must be called regularly on all the servers. These jobs must delete the same data on all the production servers. Likely this process will introduce some differences between the tables. 
## 3. Ignored table
Use two different replication streams from the source. The first stream is the regular replication link but the replica has the replication option replicate-ignore-table=t. The replication events for table t are handled by a second replication link controlled by Maxwell. The deletions events are removed and the inserts and updates are applied to the archiving replica
![](https://percona.com/blog/wp-content/uploads/2022/08/Ra_ignore_t.png)
Drawbacks:  it needs two full replication streams from the source.