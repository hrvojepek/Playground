using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Configuration;
using System.Data.OleDb;
using System.Data;

/// <summary>
/// This class allows me to connect to my db on easier way with less code
/// </summary>
public class ConnectToDatabase
{
		public static string connString = ConfigurationManager.ConnectionStrings["konekcijaNaBazu"].ConnectionString;
        
        //public static OleDbConnection konekcija = new OleDbConnection(ConfigurationManager.ConnectionStrings["konekcijaNaBazu"].ConnectionString);
 
 
    public static DataTable GetData(string query)
    {
        DataTable dt = new DataTable();
        OleDbConnection conn = new OleDbConnection(connString);
        OleDbCommand cmd = new OleDbCommand(query, conn);
        conn.Open();
        OleDbDataAdapter adapter = new OleDbDataAdapter(cmd);
        adapter.Fill(dt);
        conn.Close();
        return dt;
    }
    public static void UpdateData(string query)
    {
        OleDbConnection conn = new OleDbConnection(connString);
        OleDbCommand cmd = new OleDbCommand(query, conn);
        conn.Open();
        cmd.ExecuteNonQuery();
        conn.Close();
 
    }
    public static void DeleteData(string query)
    {
        OleDbConnection conn = new OleDbConnection(connString);
        OleDbCommand cmd = new OleDbCommand(query, conn);
        conn.Open();
        cmd.ExecuteNonQuery();
        conn.Close();
    }
    public static void InsertData(string query)
    {
        OleDbConnection conn = new OleDbConnection(connString);
        OleDbCommand cmd = new OleDbCommand(query, conn);
        conn.Open();
        cmd.ExecuteNonQuery();
        conn.Close();
 
 
    }
	
}