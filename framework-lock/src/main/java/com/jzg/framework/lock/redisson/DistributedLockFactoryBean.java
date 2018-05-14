package com.jzg.framework.lock.redisson;

import com.jzg.framework.lock.redisson.interfaces.DistributedLockTemplate;
import com.jzg.framework.lock.redisson.template.SingleDistributedLockTemplate;
import com.jzg.framework.utils.string.StringUtils;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.util.Assert;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

/**
 * 创建分布式锁模板实例的工厂Bean
 */
public class DistributedLockFactoryBean implements FactoryBean<DistributedLockTemplate> {
    private Logger logger = LoggerFactory.getLogger(DistributedLockFactoryBean.class);

    private LockInstanceMode        mode;

    private DistributedLockTemplate distributedLockTemplate;

    private RedissonClient redisson;

    /** redis地址 **/
    private String redisAddress;
    /** redis密码 **/
    private String redisPassword;
    /** redis超时时间 **/
    private int redisTimeout;

    @PostConstruct
    public void init() {
        logger.debug("初始化分布式锁模板");
        InputStream inputStream = null;
        Config config = null;
        try {
            switch (mode) {
                case SINGLE:
                    inputStream = DistributedLockFactoryBean.class.getClassLoader().getResourceAsStream("/redisson/single-conf.json");
                    config = Config.fromJSON(inputStream);
                    if(StringUtils.isNotNull(redisAddress)) config.useSingleServer().setAddress(redisAddress);
                    if(StringUtils.isNotNull(redisPassword)) config.useSingleServer().setPassword(redisPassword);
                    if(StringUtils.isNotNull(redisTimeout)) config.useSingleServer().setTimeout(redisTimeout);
                    break;
                case CLUSTER:
                    inputStream = DistributedLockFactoryBean.class.getClassLoader().getResourceAsStream("/redisson/cluster-conf.json");
                    config = Config.fromJSON(inputStream);
                    if(StringUtils.isNotNull(redisAddress)){
                        for(String address : redisAddress.split("\\,")) {
                            config.useClusterServers().addNodeAddress(address);
                        }
                    }
                    if(StringUtils.isNotNull(redisPassword)) config.useClusterServers().setPassword(redisPassword);
                    if(StringUtils.isNotNull(redisTimeout)) config.useClusterServers().setTimeout(redisTimeout);
                    break;
                case SENTINEL:
                    inputStream = DistributedLockFactoryBean.class.getClassLoader().getResourceAsStream("/redisson/sentinel-conf.json");
                    config = Config.fromJSON(inputStream);
                    if(StringUtils.isNotNull(redisAddress)){
                        for(String address : redisAddress.split("\\,")) {
                            config.useSentinelServers().addSentinelAddress(address);
                        }
                    }
                    if(StringUtils.isNotNull(redisPassword)) config.useSentinelServers().setPassword(redisPassword);
                    if(StringUtils.isNotNull(redisTimeout)) config.useSentinelServers().setTimeout(redisTimeout);
                    break;
                case MASTER_SLAVE:
                    inputStream = DistributedLockFactoryBean.class.getClassLoader().getResourceAsStream("/redisson/master-slave-conf.json");
                    config = Config.fromJSON(inputStream);
                    if(StringUtils.isNotNull(redisAddress)){
                        String[] addressArray = redisAddress.split("\\,");
                        Set<URI> slaveAddressList = new HashSet<URI>();
                        for(int i=0; i<addressArray.length; i++){
                            if(i == 0){
                                config.useMasterSlaveServers().setMasterAddress(addressArray[0]);
                            }else{
                                slaveAddressList.add(new URI(addressArray[i]));
                            }
                        }
                        config.useMasterSlaveServers().setSlaveAddresses(slaveAddressList);
                    }
                    if(StringUtils.isNotNull(redisPassword)) config.useMasterSlaveServers().setPassword(redisPassword);
                    if(StringUtils.isNotNull(redisTimeout)) config.useMasterSlaveServers().setTimeout(redisTimeout);
                    break;
            }
        } catch (URISyntaxException uriException) {
            logger.error("读取Redisson配置失败", uriException);
        } catch (IOException ioException) {
            logger.error("读取Redisson配置失败", ioException);
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    logger.error("", e);
                }
            }
        }
        Assert.notNull(config);
        redisson = Redisson.create(config);
    }

    @PreDestroy
    public void destroy() {
        logger.debug("销毁分布式锁模板");
        redisson.shutdown();
    }

    @Override
    public DistributedLockTemplate getObject() throws Exception {
        switch (mode) {
            case SINGLE:
                distributedLockTemplate = new SingleDistributedLockTemplate(redisson);
                break;
        }
        return distributedLockTemplate;
    }

    @Override
    public Class<?> getObjectType() {
        return DistributedLockTemplate.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    public void setMode(String mode) {
        if (StringUtils.isBlank(mode)) {
            throw new IllegalArgumentException("未找到dlm.redisson.mode配置项");
        }
        this.mode = LockInstanceMode.parse(mode);
        if (this.mode == null) {
            throw new IllegalArgumentException("不支持的分布式锁模式");
        }
    }

    private enum LockInstanceMode {
        SINGLE, /** 单机模式 **/
        CLUSTER, /** 集群模式 **/
        SENTINEL, /** 哨兵模式 **/
        MASTER_SLAVE; /** 主从模式 **/

        public static LockInstanceMode parse(String name) {
            for (LockInstanceMode lockMode : LockInstanceMode.values()) {
                if (lockMode.name().equals(name.toUpperCase())) {
                    return lockMode;
                }
            }
            return null;
        }
    }

    public String getRedisAddress() {
        return redisAddress;
    }

    public void setRedisAddress(String redisAddress) {
        this.redisAddress = redisAddress;
    }

    public String getRedisPassword() {
        return redisPassword;
    }

    public void setRedisPassword(String redisPassword) {
        this.redisPassword = redisPassword;
    }

    public int getRedisTimeout() {
        return redisTimeout;
    }

    public void setRedisTimeout(int redisTimeout) {
        this.redisTimeout = redisTimeout;
    }
}
