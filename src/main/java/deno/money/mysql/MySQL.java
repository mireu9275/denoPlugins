package deno.money.mysql;

import deno.money.moneyplugin.*;

import org.bukkit.ChatColor;

import java.sql.*;

public class MySQL {
    final moneyPlugin plugin;
    final String url;
    final String user;
    final String password;

    public MySQL(moneyPlugin plugin,String url, String user, String password){
        this.plugin = plugin;
        this.url = url;
        this.user = user;
        this.password = password;
    }

    public boolean conn_test(){
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        boolean result = false;
        try {
            conn = DriverManager.getConnection(url,user,password);
            pstmt = conn.prepareStatement(url);
            String Query = "SELECT * FROM USER_MAST WHERE UUID = 'TESTUUID'";
            //int success = pstmt.executeUpdate(Query); //반영된 레코드의 건수를 반환함.
            rs = pstmt.executeQuery(Query);
            rs.next();
            String uuid = rs.getString("UUID");
            if(uuid == null){//mySQL_conn_error
                plugin.getLogger().info(ChatColor.RED + "MySQL에 접속 실패하였습니다.");
            }else { //mySQL_conn_success
                plugin.getLogger().info(ChatColor.YELLOW + "MySQL에 접속하였습니다.");
                result = true;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            try{
                if(conn != null) conn.close();
                if(pstmt != null) pstmt.close();
                if(rs != null) rs.close();
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
        return result;
    }
}
