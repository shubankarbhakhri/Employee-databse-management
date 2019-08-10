import java.applet.*;
import java.awt.event.*;
import java.awt.*;
import java.sql.*;
import javax.swing.*;
public class empdatabase extends Frame implements ActionListener
{	
	 Label l1,l2,l3;
	TextField t1,t2,t3;
                    Button b1,b2,b3,b4,b5;
                    Panel p1,p2,p3;
	Connection con;
	PreparedStatement pstmt;
	Statement stmt;
	empdatabase()
	{
		getConnection();
		p1=new Panel(new GridLayout(10,1));
		p2=new Panel(new GridLayout(10,1));
		p3=new Panel();
		l1=new Label("code");
		l2=new Label("Name");
		l3=new Label("Basic");
		t1=new TextField(8);
		t2=new TextField(8);
		t3=new TextField(8);
		b1=new Button("Save");
		b2=new Button("Exit");
		b3=new Button("Retrieve");
		b4=new Button("update");
		b5=new Button("delete");
		p1.add(l1);
		p1.add(l2);
		p1.add(l3);
		p2.add(t1);
		p2.add(t2);
		p2.add(t3);
		p3.add(b1);
		p3.add(b2);
		p3.add(b3);
		p3.add(b4);
		p3.add(b5);
		add(p1,BorderLayout.WEST);
		add(p2,BorderLayout.EAST);
		add(p3,BorderLayout.SOUTH);
		b1.addActionListener(this);
		b2.addActionListener(this);
		b3.addActionListener(this);
		b4.addActionListener(this);
		b5.addActionListener(this);
		setSize(500,500);
		setVisible(true);
	}
	void getConnection()
	{
		try
		{
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
		}
		catch(Exception e)
		{
		}
		try
		{
			con=DriverManager.getConnection("jdbc:odbc:shubh");
		}
		catch(SQLException e)
		{
		}
		JOptionPane.showMessageDialog(null,con);
	}
	public void actionPerformed(ActionEvent ae)
	{
		if(ae.getSource()==b1)
		{
			save();
		}
		if(ae.getSource()==b3)
		{
			retrieve();
		}
		if(ae.getSource()==b4)
		{
			UPDATE();
		}
		if(ae.getSource()==b5)
		{
			DELETE();
		}
	}
	
	void save()
	{
		try
		{
			
			String str="insert into employee values(?,?,?)";
			pstmt=con.prepareStatement(str);
			stmt=con.createStatement();
			pstmt.setString(1,t1.getText());
			pstmt.setString(2,t2.getText());
			pstmt.setString(3,t3.getText());
			ResultSet rs=stmt.executeQuery("Select * from Employee where code ='"+t1.getText()+"'");
			if(rs.next())
			{
				JOptionPane.showMessageDialog(null,"Record already exists");	
			}
			else
			{
				pstmt.executeUpdate();
				JOptionPane.showMessageDialog(null,"Record Saved");
			}		
		}
		catch(SQLException e)
		{
		}		

	}
	void retrieve()
	{
		try
		{
			stmt =con.createStatement();
			ResultSet rs=stmt.executeQuery("Select * from Employee where code = '"+t1.getText()+"'");
			if(rs.next())
			{
				t2.setText(rs.getString(2));
				t3.setText(rs.getString(3));
			}
			else
			{
				JOptionPane.showMessageDialog(null,"record not found");
			}
		}
		catch(SQLException e)
		{
		}
	}
			
	void UPDATE()
	{
		try
		{
			stmt=con.createStatement();
			String str="update employee set name='"+t2.getText()+"', basic='"+t3.getText()+"' where code='"+t1.getText()+"'";
			
			stmt.executeUpdate(str);		
			JOptionPane.showMessageDialog(null,"record updated");
		}
		catch(SQLException e)
		{
		}		

	}

	void DELETE()
	{
		try
		{
			
			String str="delete from employee where code='"+t1.getText()+"'";
			stmt=con.createStatement();
			stmt.executeUpdate(str);		
			JOptionPane.showMessageDialog(null,"record deleted");
			t1.setText("");
			t2.setText("");
			t3.setText("");

		}
		catch(SQLException e)
		{
		}		

	}
	



	public static void main(String args[])
	{
		empdatabase x= new empdatabase();
	
	}
}