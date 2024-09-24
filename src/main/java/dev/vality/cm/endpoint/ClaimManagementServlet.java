package dev.vality.cm.endpoint;

import dev.vality.cm.meta.UserIdentityEmailExtensionKit;
import dev.vality.cm.meta.UserIdentityIdExtensionKit;
import dev.vality.cm.meta.UserIdentityRealmExtensionKit;
import dev.vality.cm.meta.UserIdentityUsernameExtensionKit;
import dev.vality.damsel.claim_management.ClaimManagementSrv;
import dev.vality.woody.thrift.impl.http.THServiceBuilder;
import lombok.RequiredArgsConstructor;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import java.io.IOException;
import java.util.List;

@WebServlet("/v1/cm")
@RequiredArgsConstructor
public class ClaimManagementServlet extends GenericServlet {

    private final ClaimManagementSrv.Iface claimManagementHandler;
    private Servlet thriftServlet;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        thriftServlet = new THServiceBuilder()
                .withMetaExtensions(
                        List.of(
                                UserIdentityIdExtensionKit.INSTANCE,
                                UserIdentityUsernameExtensionKit.INSTANCE,
                                UserIdentityEmailExtensionKit.INSTANCE,
                                UserIdentityRealmExtensionKit.INSTANCE
                        )
                )
                .build(ClaimManagementSrv.Iface.class, claimManagementHandler);
    }

    @Override
    public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
        thriftServlet.service(req, res);
    }
}
