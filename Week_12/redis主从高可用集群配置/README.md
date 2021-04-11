### 主从复制

redis01.conf
```shell
bind 127.0.0.1
port 6379
dir D:\rediss\redis01\data
```

启动命令
```shell
redis-server redis01.conf
redis-server redis02.conf
redis-server redis03.conf
rediscli -h 127.0.0.1 -p 6380
slaveof 127.0.0.1 6379
```

### Sentinel

sentinel01.config

```shell
sentinel deny-scripts-reconfig yes
sentinel monitor mymaster 127.0.0.1 6380 2
sentinel down-after-milliseconds mymaster 60000
bind 127.0.0.1
port 6381
dir D:\rediss\sentinel01\data
```

启动命令

```shell
redis-server redis01.conf
redis-server redis02.conf
redis-server redis03.conf
rediscli -h 127.0.0.1 -p 6380
slaveof 127.0.0.1 6379
redis-server redis03.conf
rediscli -h 127.0.0.1 -p 6381
slaveof 127.0.0.1 6379
redis-server sentinel01.config --sentinel
redis-server sentinel02.config --sentinel
redis-server sentinel03.config --sentinel
```

### Cluster

cluster01.config

```shell
cluster-enabled yes
cluster-config-file D:\rediss\cluster\nodes01.conf
bind 127.0.0.1
port 6391
dir D:\rediss\cluster\data01
```

启动命令

```shell
redis-server cluster01.conf
redis-server cluster02.conf
redis-server cluster03.conf
redis-server cluster04.conf
redis-server cluster05.conf
redis-server cluster06.conf
redis-cli --cluster create 127.0.0.1:6391 127.0.0.1:6392 127.0.0.1:6393 127.0.0.1:6394 127.0.0.1:6395 127.0.0.1:6396 --cluster-replicas 1
redis-cli -c -h 127.0.0.1 -p 6391 cluster nodes
```
