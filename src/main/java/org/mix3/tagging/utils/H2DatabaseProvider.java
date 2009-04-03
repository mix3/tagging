package org.mix3.tagging.utils;

import java.sql.Driver;
import net.java.ao.db.HSQLDatabaseProvider;

public class H2DatabaseProvider extends HSQLDatabaseProvider {
    public H2DatabaseProvider(String uri, String username, String password) {
        super(uri, username, password);
    }
    
    public Class< ? extends Driver> getDriverClass() throws ClassNotFoundException {  
        return (Class< ? extends Driver>) Class.forName("org.h2.Driver");
    }
} 