package main;
import java.io.*;import lexer.*;import parser.*;
import main.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;


import lexer.Lexer;
import parser.Parser;

//

public class CompilerPhase {
	public static JTextArea mytext1,mytext2,mytext3,mytext4,mytext5,mytext6;
	public static JLabel lvar = new JLabel();
	public static JLabel svar = new JLabel();
	public final static String newLine ="\n";
	public static File selectedFile=null;
	@SuppressWarnings("deprecation")
	public static void main(String[] args) {
			
			JFrame frame=new JFrame();
			frame.setVisible(true);
			frame.setTitle("Compiler Front End");
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setBounds(0,0,1400,800);
			frame.setBackground(Color.DARK_GRAY);
		
			
			Container c=frame.getContentPane();
			c.setLayout(null);
			c.setBackground(new Color(224, 217, 159));
			
			JFrame flexical = new JFrame();
			flexical.setTitle("Lexical Phase");
			//flexical.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			flexical.setBounds(850,0,400,400);
			flexical.setBackground(Color.DARK_GRAY);
			Container l=flexical.getContentPane();
			l.setLayout(null);
		    l.add(lvar);
		    lvar.setBounds(100,0,300,30);
			
			JFrame fsyntax = new JFrame();
			fsyntax.setTitle("Syntax Phase");
			//fsyntax.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			fsyntax.setBounds(850,100,400,400);
			fsyntax.setBackground(Color.DARK_GRAY);
			Container s=fsyntax.getContentPane();
			s.setLayout(null);
			
			JFrame finterm = new JFrame();
			finterm.setTitle("Intermediate Phase");
			//finterm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			finterm.setBounds(850,200,400,400);
			finterm.setBackground(Color.DARK_GRAY);
			Container i=finterm.getContentPane();
			i.setLayout(null);
			
			
			JFrame fsymbol = new JFrame();
			fsymbol.setTitle("Symbol Table");
			//fsymbol.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			fsymbol.setBounds(850,300,400,400);
			fsymbol.setBackground(Color.DARK_GRAY);
			Container sy=fsymbol.getContentPane();
			sy.setLayout(null);
			sy.add(svar);
			svar.setBounds(100,0,300,30);
			
			// Applet //
			
			JLabel label1=new JLabel("Compiler Front End");
			label1.setBounds(100, 25, 350, 30);
			c.add(label1);
			Font f=new Font("Arial",Font.BOLD,30);
			label1.setFont(f);
			label1.setBackground(Color.MAGENTA);
			label1.setForeground(Color.BLUE);
			
				
			// UPPER BUTTONS //
			
			JButton btn1b=new JButton("TEXT");
			btn1b.setBounds(50, 100, 100, 30);
			c.add(btn1b);
			btn1b.setFont(new java.awt.Font("Arial", Font.BOLD, 14));
			btn1b.setBackground(Color.WHITE);
			btn1b.setForeground(Color.BLUE);
			
			btn1b.addActionListener(new ActionListener() {
			    @Override
			    public void actionPerformed(ActionEvent ev){
			    	mytext1.setText("");
			    }});

			
			
				//
				
			JButton btn2b=new JButton("BROWSE");
			btn2b.setBounds(160, 100, 100, 30);
			c.add(btn2b);
			//btn2b.addActionListener(new Handler());
			btn2b.setFont(new java.awt.Font("Arial", Font.BOLD, 14));
			btn2b.setBackground(Color.YELLOW);
			btn2b.setForeground(Color.BLUE);
			
			
			
			
			mytext1 = new JTextArea();
			btn2b.addActionListener(new ActionListener() {
			    @Override
			    public void actionPerformed(ActionEvent ev){
					JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
					int returnValue = jfc.showOpenDialog(mytext1);
					if(returnValue == JFileChooser.APPROVE_OPTION){	
					selectedFile = jfc.getSelectedFile();
					
					try{
						FileInputStream input = new FileInputStream(selectedFile);
						byte fc1[]=new byte[(int)selectedFile.length()];
						input.read(fc1);
						String str=new String(fc1);
						mytext1.setText(str);
						
						} catch (Exception e){
							e.printStackTrace();
						
						}
					}
			        // do everything here...
			    }
			});
			mytext1.setMargin(new Insets(10, 10, 10, 10));
			Font f3=new Font("Consolas",Font.PLAIN,15);
			mytext1.setFont(f3);
              
              
			
			//mytext1.setWrapStyleWord(true);
			
			
			mytext2 = new JTextArea();
			mytext2.setBounds(700, 150, 400, 330);
			
			c.add(mytext2);
			mytext2.setForeground(new Color(204, 34, 34));
			Font f1=new Font("Consolas",Font.ITALIC|Font.BOLD,15);
			mytext2.setFont(f1);
			
			
			JScrollPane scrolltxt = new JScrollPane(mytext2);
		 	scrolltxt.setSize(400,300);
			scrolltxt.show();	
		    scrolltxt.setBounds(700, 150, 400, 330);
				c.add(scrolltxt);
				scrolltxt.setBorder(BorderFactory.createLineBorder(Color.gray,5));
				mytext2.setMargin(new Insets(10, 10, 10, 10));
				//mytext2.setVisible(false);
				
				
				mytext3 = new JTextArea();
				mytext3.setBounds(20, 30, 360, 330);
				
				l.add(mytext3);
				/*
				JScrollPane scrolltxt3 = new JScrollPane(mytext3);
			 	scrolltxt3.setSize(400,300);
				scrolltxt3.show();	
			    scrolltxt3.setBounds(700, 150, 400, 330);
					c.add(scrolltxt3);   */
					
					//mytext3.setVisible(false);
				
				
				mytext4 = new JTextArea();
				mytext4.setBounds(20, 10, 360, 350);
				
				s.add(mytext4);
				
				mytext5 = new JTextArea();
				mytext5.setBounds(20, 10, 360, 350);
				
				i.add(mytext5);
				
				mytext6 = new JTextArea();
				mytext6.setBounds(20, 30, 360, 330);
				
				sy.add(mytext6);
				
				
			//
			
			JButton btn3b=new JButton("RUN");
			btn3b.setBounds(270, 100, 100, 30);
			c.add(btn3b);
			btn3b.setFont(new java.awt.Font("Arial", Font.BOLD, 14));
			btn3b.setBackground(Color.GREEN);
			btn3b.setForeground(Color.BLUE);
			
			btn3b.addActionListener(new ActionListener() {
			    @Override
			    public void actionPerformed(ActionEvent ev){
			    	mytext2.setText("");
			    	mytext3.setText("");
			    	mytext4.setText("");
			    	mytext5.setText("");
			    	mytext6.setText("");
			    
			    	Lexer lex = new Lexer();
			    	Parser parse;
					try {
						lex.line=0;
						parse = new Parser(lex);
						parse.program();
				    	System.out.write('\n');
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			    }
			});
							
			// TEXT FIELD //
			
		//  JTextArea mytext1 = new JTextArea(20,20);
			mytext1.setBounds(30, 150, 600, 330);
			
			c.add(mytext1);
			
			    JScrollPane scrolltxt1 = new JScrollPane(mytext1);
			 	scrolltxt1.setSize(800,330);
				scrolltxt1.show();	
			    scrolltxt1.setBounds(30, 150, 600, 330);
					c.add(scrolltxt1);		
                  
					TextLineNumber line=new TextLineNumber(mytext1);
					scrolltxt1.setRowHeaderView(line);
					scrolltxt1.setBorder(BorderFactory.createLineBorder(Color.gray,5));
			
			
			// LOWER BUTTONS //
			
			JButton btn1=new JButton("Lexical");
			btn1.setBounds(30, 500, 150, 30);
			c.add(btn1);	
			btn1.setFont(new java.awt.Font("Arial", Font.BOLD, 14));
			btn1.setBackground(Color.YELLOW);
			btn1.setForeground(Color.BLUE);
			
			btn1.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					flexical.setVisible(true);
					mytext3.setVisible(true);
					lvar.setVisible(true);
					lvar.setText("Lexeme               Token");
				}
			});
			
			
			JButton btn2=new JButton("Syntax");
			btn2.setBounds(180, 500, 150, 30);
			c.add(btn2);
			btn2.setFont(new java.awt.Font("Arial", Font.BOLD, 14));
			btn2.setBackground(Color.YELLOW);
			btn2.setForeground(Color.BLUE);
			
           btn2.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					fsyntax.setVisible(true);
					mytext4.setVisible(true);
				}
			});
			
			
			/*JButton btn3=new JButton("Semantic");
			btn3.setBounds(250, 500, 100, 30);
			c.add(btn3);
			btn3.setFont(new java.awt.Font("Arial", Font.BOLD, 14));
			btn3.setBackground(Color.YELLOW);
			btn3.setForeground(Color.BLUE);*/
			
			
			JButton btn4=new JButton("Intermediate");
			btn4.setBounds(330, 500, 150, 30);
			c.add(btn4);
			btn4.setFont(new java.awt.Font("Arial", Font.BOLD, 12));
			btn4.setBackground(Color.YELLOW);
			btn4.setForeground(Color.BLUE);
			
            btn4.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					finterm.setVisible(true);
					mytext5.setVisible(true);
				}
			});
			
			/*
			JButton btn5=new JButton("Optimization");
			btn5.setBounds(490, 500, 120, 30);
			c.add(btn5);
			btn5.setFont(new java.awt.Font("Arial", Font.BOLD, 12));
			btn5.setBackground(Color.YELLOW);
			btn5.setForeground(Color.BLUE);
			*/
			/*
			JButton btn6=new JButton("Generation");
			btn6.setBounds(620, 500, 100, 30);
			c.add(btn6);
			btn6.setFont(new java.awt.Font("Arial", Font.BOLD, 12));
			btn6.setBackground(Color.YELLOW);
			btn6.setForeground(Color.BLUE);
			
			*/
			JButton btn7=new JButton("Symbol");
			btn7.setBounds(480, 500, 150, 30);
			c.add(btn7);
			btn7.setFont(new java.awt.Font("Arial", Font.BOLD, 14));
			btn7.setBackground(Color.YELLOW);
			btn7.setForeground(Color.BLUE);
			
            btn7.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					fsymbol.setVisible(true);
					mytext6.setVisible(true);
					svar.setVisible(true);
					svar.setText("Variable           Type");
				}
			});
			
		}
	}


