# strengthlimit
A bukkit plugin for minecraft 1.7.10 used for set up strength limits in RPG server.

一个客户定制的bukkit服务端插件，用于在RPG服务区里限制每天副本传送次数，版本1.7.10。

## 变量
%strengthlimit_strength% 玩家当前体力值

%strengthlimit_timeleft% 副本剩余时间（单位：秒）

## 命令
/addstrength <玩家ID> <体力值> 为玩家增加体力值

/resetstrength 重置体力值，每日限一次

## 权限节点
strengthlimit.reset: 重置体力权限 默认所有人拥有

strengthlimit.add: 增加体力权限 默认OP权限

strengthlimit.vip: VIP体力权限 默认无人拥有
