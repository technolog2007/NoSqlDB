package shpp.com.repo;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.TransportUtils;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.elasticsearch.client.RestClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import shpp.com.util.MyException;
import shpp.com.util.PropertiesLoader;

import javax.net.ssl.SSLContext;
import java.util.Properties;


public class ConnectToDB {
    private static final String PROPERTIES_FILE = "app.properties";
    private static final Logger logger = LoggerFactory.getLogger(ConnectToDB.class);
    RestClient restClient;
    ElasticsearchTransport transport;
    ElasticsearchClient client;

//    public ConnectToDB() throws MyException {
//        // Create the low-level client
//        restClient = RestClient.builder(
//                new HttpHost(getProperty("host"), Integer.parseInt(getProperty("port")))).build();
//        // Create the transport with a Jackson mapper
//        transport = new RestClientTransport(restClient, new JacksonJsonpMapper());
//        // And create the API client
//        client = new ElasticsearchClient(transport);
//    }

    public ConnectToDB() throws MyException {
        String fingerprint = "<certificate fingerprint>";

        SSLContext sslContext = TransportUtils.sslContextFromCaFingerprint(fingerprint);

        BasicCredentialsProvider credsProv = new BasicCredentialsProvider();
        credsProv.setCredentials(
                AuthScope.ANY, new UsernamePasswordCredentials("elastic", "_6qJ42XhahvQRmOK*Hdp")
        );

        restClient = RestClient
                .builder(new HttpHost("host", Integer.parseInt(getProperty("port")), "https"))
                .setHttpClientConfigCallback(hc -> hc
                        .setSSLContext(sslContext)
                        .setDefaultCredentialsProvider(credsProv)
                )
                .build();

        // Create the transport and the API client
        transport = new RestClientTransport(restClient, new JacksonJsonpMapper());
        client = new ElasticsearchClient(transport);
    }

    public RestClient getRestClient() {
        return restClient;
    }

    public ElasticsearchClient getClient() {
        return client;
    }

    public ElasticsearchTransport getTransport() {
        return transport;
    }

    /**
     * Method to load and restore all properties from a property file
     *
     * @return - property
     */
    private static Properties loadProperties() {
        return new PropertiesLoader().loadProperties(PROPERTIES_FILE);
    }

    /**
     * The method returns the selected property as a string
     *
     * @param property - property name
     * @return - property values from the properties file
     */
    private static String getProperty(String property) throws MyException {
        String url = loadProperties().getProperty(property);
        if (url != null) {
            return url;
        } else {
            throw new MyException("Sorry! Please enter " + property + " required for connection!");
        }
    }
}