package com.jzg.framework.search.solr;

import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.beans.DocumentObjectBinder;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrInputDocument;
import org.apache.solr.common.params.SolrParams;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class HttpServer {
    HttpSolrServer server = null;

    private int soTimeout = 1000;
    private int connectionTimeout = 100;
    private int defaultMaxConnectionsPerHost = 100;
    private int maxTotalConnections = 100;
    private boolean followRedirects = false;
    private boolean allowCompression = true;
    private int maxRetries = 1;


    public int getSoTimeout() {
        return soTimeout;
    }

    public void setSoTimeout(int soTimeout) {
        this.soTimeout = soTimeout;
    }

    public int getConnectionTimeout() {
        return connectionTimeout;
    }

    public void setConnectionTimeout(int connectionTimeout) {
        this.connectionTimeout = connectionTimeout;
    }

    public int getDefaultMaxConnectionsPerHost() {
        return defaultMaxConnectionsPerHost;
    }

    public void setDefaultMaxConnectionsPerHost(int defaultMaxConnectionsPerHost) {
        this.defaultMaxConnectionsPerHost = defaultMaxConnectionsPerHost;
    }

    public int getMaxTotalConnections() {
        return maxTotalConnections;
    }

    public void setMaxTotalConnections(int maxTotalConnections) {
        this.maxTotalConnections = maxTotalConnections;
    }

    public boolean isFollowRedirects() {
        return followRedirects;
    }

    public void setFollowRedirects(boolean followRedirects) {
        this.followRedirects = followRedirects;
    }

    public boolean isAllowCompression() {
        return allowCompression;
    }

    public void setAllowCompression(boolean allowCompression) {
        this.allowCompression = allowCompression;
    }

    public int getMaxRetries() {
        return maxRetries;
    }

    public void setMaxRetries(int maxRetries) {
        this.maxRetries = maxRetries;
    }



    private HttpServer(){

    }

    public HttpServer(String serverUrl) {
        this.server = new HttpSolrServer(serverUrl);
        this.server.setSoTimeout(this.soTimeout);  // socket read timeout
        this.server.setConnectionTimeout(this.connectionTimeout);
        this.server.setDefaultMaxConnectionsPerHost(this.defaultMaxConnectionsPerHost);
        this.server.setMaxTotalConnections(this.maxTotalConnections);
        this.server.setFollowRedirects(this.followRedirects);  // defaults to false
        // allowCompression defaults to false.
        // Server side must support gzip or deflate for this to have any effect.
        this.server.setAllowCompression(this.allowCompression);
        this.server.setMaxRetries(this.maxRetries); // defaults to 0.  > 1 not recommended.
    }

    /**
     * 生成索引
     * @param documents
     * @throws IOException
     * @throws SolrServerException
     */
    public void add(Collection<SolrInputDocument> documents) throws IOException, SolrServerException {
        this.server.add(documents);
    }


    /**
     * 提交多个bean至Solr
     * @param beans
     * @param <T>
     * @throws IOException
     * @throws SolrServerException
     */
    public <T> void addBeans(Collection<T> beans) throws IOException, SolrServerException {
        this.server.addBeans(beans);
    }

    /**
     * 提交bean至solr
     * @param bean
     * @param <T>
     * @throws IOException
     * @throws SolrServerException
     */
    public <T> void addBean(T bean) throws IOException, SolrServerException {
        this.server.addBean(bean);
    }

    /**
     * 删除主键对应的索引
     * @param Id
     * @throws IOException
     * @throws SolrServerException
     */
    public void delete(String Id) throws IOException, SolrServerException {
        this.server.deleteById(Id);
    }

    /**
     * 删除主键列表对应的索引
     * @param Ids
     * @throws IOException
     * @throws SolrServerException
     */
    public void delete(List<String> Ids) throws IOException, SolrServerException {
        this.server.deleteById(Ids);
    }


    /**
     * 删除主键范围对应的索引
     * @param beginSysno
     * @param endSysno
     * @throws IOException
     * @throws SolrServerException
     */
    public void delete(long beginSysno, long endSysno) throws IOException, SolrServerException {
        List<String> sysnos = new ArrayList<String>();
        for (long i = beginSysno + 1; i < endSysno + 1; i++) {
            sysnos.add(Long.toString(i));
        }
        if (sysnos.size() > 0)
            delete(sysnos);
    }

    /**
     * 提交请求
     * @throws IOException
     * @throws SolrServerException
     */
    public void commit() throws IOException, SolrServerException {
        this.server.commit();
    }

    /**
     * 提交请求
     * @param waitFlush
     * @param waitSearcher
     * @throws IOException
     * @throws SolrServerException
     */
    public void commit(boolean waitFlush, boolean waitSearcher) throws IOException, SolrServerException {
        this.server.commit(waitFlush, waitSearcher);
    }


    /**
     * 查询
     * @param params 查询条件
     * @return
     * @throws SolrServerException
     */
    public QueryResponse query(SolrParams params) throws SolrServerException {
        return this.server.query(params);
    }

    public DocumentObjectBinder getBinder() {
        return this.server.getBinder();
    }
}
