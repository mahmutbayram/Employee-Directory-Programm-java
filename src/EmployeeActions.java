/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mm
 */
public class EmployeeActions {
    private Connection con = null;
    
    private Statement statement = null;
    private PreparedStatement preparedStatement = null;
    

    
    public ArrayList<Employee> getEmployee(){
        
        ArrayList<Employee> resultant = new ArrayList<Employee>();
        
        
        try {
            statement = con.createStatement();
            String query = "Select * From employee";
        
            ResultSet rs = statement.executeQuery(query);
            
            while (rs.next()){
                
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String surname = rs.getString("surname");
                String department = rs.getString("department");
                int salary = rs.getInt("salary");
                        
                resultant.add(new Employee(id, name, surname, department,salary));
                

                        
            }
            return resultant;
            
        } catch (SQLException ex) {
            Logger.getLogger(EmployeeActions.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        
        
        
        
                
                
    }
    public void addEmployee(String name, String surname, String department, int salary){
            
        String query = "Insert Into employee(name,surname,department,salary) VALUES(?,?,?,?)";
        try {
            preparedStatement = con.prepareStatement(query);
            
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, surname);
            preparedStatement.setString(3, department);
            preparedStatement.setInt(4, salary);
            
            preparedStatement.executeUpdate();


        } catch (SQLException ex) {
            Logger.getLogger(EmployeeActions.class.getName()).log(Level.SEVERE, null, ex);
        }
            
            
            
        
    }
    public void updateEmployee(int id, String new_name, String new_surname, String new_department,int new_salary) {
        String query =  "Update employee Set name = ? , surname = ? , department = ? , salary = ? where id = ?";
        
        try {
            preparedStatement = con.prepareStatement(query);


            preparedStatement.setString(1,new_name);
            preparedStatement.setString(2,new_surname);

            preparedStatement.setString(3,new_department);
            preparedStatement.setInt(4,new_salary);

            preparedStatement.setInt(5, id);

            preparedStatement.executeUpdate();



      } catch (SQLException ex) {
            Logger.getLogger(EmployeeActions.class.getName()).log(Level.SEVERE, null, ex);
        
            }
        }
    public void deleteEmployee (int id){
            
        String query = "Delete From employee where id = ?";
        
        try {
            preparedStatement = con.prepareStatement(query);
            preparedStatement.setInt(1, id);

            preparedStatement.executeUpdate();

      } catch (SQLException ex) {
                Logger.getLogger(EmployeeActions.class.getName()).log(Level.SEVERE, null, ex);
        }
                
        
}
    public boolean Login (String username,String password){
        String query = "Select * From admins where username = ? and password = ? ";
        
        try {
            preparedStatement = con.prepareStatement(query);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            
            ResultSet rs = preparedStatement.executeQuery();
            
            /*if (rs.next()== false){
                return false;
            }
            else {
                return true;
            }*/
            return rs.next();
            
        } catch (SQLException ex) {
            Logger.getLogger(EmployeeActions.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        
    
    }   
    public EmployeeActions() {
        String url = "jdbc:mysql://" + Database.host + ":" + Database.port + "/" + Database.db_name+ "?useUnicode=true&characterEncoding=utf8";
        
        
        try {
            
            Class.forName("com.mysql.jdbc.Driver");
            
        } catch (ClassNotFoundException ex) {
            System.out.println("Driver is not found....");
        }
        
        
        try {
            con = DriverManager.getConnection(url, Database.username, Database.password);
            System.out.println("Sql connection success...");
            
            
        } catch (SQLException ex) {
            System.out.println("Sql connection was not successfull...");
            ex.printStackTrace();
        }
}   

                
}

 