package wiki.tony.chat.comet.operation;

import io.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import wiki.tony.chat.base.service.AuthService;
import wiki.tony.chat.base.bean.Proto;

/**
 * 认证操作
 */
@Component
public class AuthOperation extends AbstractOperation {

    private final Logger logger = LoggerFactory.getLogger(AuthOperation.class);

    public static final int OP = 0;
    public static final int OP_REPLY = 1;

    @Value("${server.id}")
    private int serverId;
    @Autowired
    private AuthService authService;

    @Override
    public Integer op() {
        return OP;
    }

    @Override
    public void action(Channel ch, Proto proto) throws Exception {
        // connection auth
        setKey(ch, authService.auth(serverId, proto));

        // write reply
        proto.setOperation(OP_REPLY);
        proto.setBody(null);
        ch.writeAndFlush(proto);

        logger.debug("auth ok");
    }

}
