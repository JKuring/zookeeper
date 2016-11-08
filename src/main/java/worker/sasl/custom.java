package worker.sasl;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.server.ServerCnxn;
import org.apache.zookeeper.server.auth.AuthenticationProvider;

/**
 * Created by linghang.kong on 2016/10/25.
 */
public class custom implements AuthenticationProvider {
    @Override
    public String getScheme() {
        return null;
    }

    @Override
    public KeeperException.Code handleAuthentication(ServerCnxn cnxn, byte[] authData) {
        return null;
    }

    @Override
    public boolean matches(String id, String aclExpr) {
        return false;
    }

    @Override
    public boolean isAuthenticated() {
        return false;
    }

    @Override
    public boolean isValid(String id) {
        return false;
    }
}
