package curator;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.api.CuratorEvent;
import org.apache.curator.framework.api.CuratorListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by linghang.kong on 2016/10/31.
 */
public class Listener implements CuratorListener {
    private static final Logger logger = LoggerFactory.getLogger(Listener.class);

    @Override
    public void eventReceived(CuratorFramework curatorFramework, CuratorEvent curatorEvent) throws Exception {
        try {
            switch (curatorEvent.getType()) {
                case CREATE:
                    break;
                case DELETE:
                    break;
                case EXISTS:
                    break;
                case GET_DATA:
                    break;
                case SET_DATA:
                    break;
                case CHILDREN:
                    break;
                case SYNC:
                    break;
                case GET_ACL:
                    break;
                case SET_ACL:
                    break;
                case WATCHED:
                    break;
                case CLOSING:
                    break;
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }
}
