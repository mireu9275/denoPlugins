package deno.mysql;

import deno.plugin.*;

import org.bukkit.ChatColor;

import java.sql.*;

public class MySQL {
    final Plugin plugin;
    public static String url;
    public static String user;
    public static String password;

    public MySQL(Plugin plugin, String url, String user, String password){
        this.plugin = plugin;
        MySQL.url = url;
        MySQL.user = user;
        MySQL.password = password;
    }

    public boolean conn_test(String UUID){
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = DriverManager.getConnection(url,user,password);
            pstmt = conn.prepareStatement(url);
            String Query = "SELECT * FROM USER_MAST WHERE UUID = '" + UUID + "'";
            //int success = pstmt.executeUpdate(Query); //반영된 레코드의 건수를 반환함.
            rs = pstmt.executeQuery(Query);
            rs.next();
            String uuid = rs.getString("UUID");
            if(uuid == null){//mySQL_conn_error
                plugin.getLogger().info(ChatColor.RED + "MySQL에 접속 실패하였습니다.");
                return false;
            }else { //mySQL_conn_success
                plugin.getLogger().info(ChatColor.YELLOW + "MySQL에 접속하였습니다.");
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
        return true;
    }

    public void pFirstJoin(String uuid, String name){
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try{
            conn = DriverManager.getConnection(url,user,password);
            pstmt = conn.prepareStatement(url);
            String Query = "CALL SP_PFIRSTJOIN('" + uuid + "','" + name + "')";
            rs = pstmt.executeQuery(Query);
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
    }
}
