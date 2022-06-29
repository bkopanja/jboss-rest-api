package rs.ktech.CRUDAndRestApi.mvc.controllers;

import javax.naming.InitialContext;
import javax.sql.DataSource;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

@Path("/import-external-users")
public class ExternalApiUserController {
    @GET
    @Produces("text/plain")
    public String importExternalUsers() {

        try {
            InitialContext ctx = new InitialContext();
            DataSource ds = (DataSource)ctx.lookup("java:jboss/datasources/PostgresDS");
            Connection conn = ds.getConnection();

            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("select table_schema, table_name from information_schema.tables");
            while (rs.next()) {
                System.out.format("%s: %s\n", rs.getString(1), rs.getString(2));
            }
            rs.close();
            st.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "done";
    }
}