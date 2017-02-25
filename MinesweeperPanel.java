/*
 * Mitchell Golding 3552573
 * 17 February 2017
 * CS 1083 Assignment 3
 *
 * Purpose: To play minesweeper
 * Input: Clicks
 * Output: Numbers and mines
 */
package cs1083_assign3;
import javax.swing.*; import java.awt.*; import java.awt.event.*;import java.util.*; import java.io.*;
public class MinesweeperPanel extends JPanel{
    private JButton face;
    private ImageIcon norm,win,dead,mine,flag;
    private JLabel mode,flags;
    private JPanel field;
    private String input;
    private int x = -123,y=-134,custminecount=-1;
    private boolean minemode = true, first = true,custmine = false;
    private JButton[][] Bfield;
    private int[][] adj;
    private Random rng = new Random();
    private int minesleft;
    private int flagcount=0;
    private GregorianCalendar start, end;
    private Scanner scorein;
    private PrintStream scoreout;
    private File scores = new File("Scores.txt");
    
    public static void ZeroClear(int i, int a, int x, int y, JButton[][] Bfield,int[][] adj)
    {
        if(i>0)//Check above
        {
            if(!Bfield[i-1][a].getText().equals("FLAG"))
            {
                if(adj[i-1][a]==0&&Bfield[i-1][a].isEnabled())
                {
                    Bfield[i-1][a].setEnabled(false);
                    Bfield[i-1][a].setText("");
                    ZeroClear(i-1,a,x,y,Bfield,adj);
                }
                else if(adj[i-1][a]!=0&&Bfield[i-1][a].isEnabled())
                {
                    Bfield[i-1][a].setEnabled(false);
                    Bfield[i-1][a].setText(""+adj[i-1][a]);
                }
            }
        }
        if(i<y-1)//Check Below
        {
            if(!Bfield[i+1][a].getText().equals("FLAG"))
            {
                if(adj[i+1][a]==0&&Bfield[i+1][a].isEnabled())
                {
                    Bfield[i+1][a].setEnabled(false);
                    Bfield[i+1][a].setText("");
                    ZeroClear(i+1,a,x,y,Bfield,adj);
                }
                else if(adj[i+1][a]!=0&&Bfield[i+1][a].isEnabled())
                {
                    Bfield[i+1][a].setEnabled(false);
                    Bfield[i+1][a].setText(""+adj[i+1][a]);
                }
            }
        }
        if(a>0)//Check Left
        {
            if(!Bfield[i][a-1].getText().equals("FLAG"))
            {
                if(adj[i][a-1]==0&&Bfield[i][a-1].isEnabled())
                {
                    Bfield[i][a-1].setEnabled(false);
                    Bfield[i][a-1].setText("");
                    ZeroClear(i,a-1,x,y,Bfield,adj);
                }
                else if(adj[i][a-1]!=0&&Bfield[i][a-1].isEnabled())
                {
                    Bfield[i][a-1].setEnabled(false);
                    Bfield[i][a-1].setText(""+adj[i][a-1]);
                }
            }
        }
        if(a<x-1)//Check Right
        {
            if(!Bfield[i][a+1].getText().equals("FLAG"))
            {
                if(adj[i][a+1]==0&&Bfield[i][a+1].isEnabled())
                {
                    Bfield[i][a+1].setEnabled(false);
                    Bfield[i][a+1].setText("");
                    ZeroClear(i,a+1,x,y,Bfield,adj);
                }
                else if(adj[i][a+1]!=0&&Bfield[i][a+1].isEnabled())
                {
                    Bfield[i][a+1].setEnabled(false);
                    Bfield[i][a+1].setText(""+adj[i][a+1]);
                }
            }
        }
        //Diagonals only go if the diagonal is a numbered space
        if(i>0&&a>0)//Check Up Left
        {
            if(!Bfield[i-1][a-1].getText().equals("FLAG"))
            {
                if(adj[i-1][a-1]==0&&Bfield[i-1][a-1].isEnabled())
                {
                    ;
                }
                else if(adj[i-1][a-1]!=0&&Bfield[i-1][a-1].isEnabled())
                {
                    Bfield[i-1][a-1].setEnabled(false);
                    Bfield[i-1][a-1].setText(""+adj[i-1][a-1]);
                }
            }
        }
        if(i>0&&a<x-1)//Check Up Right
        {
            if(!Bfield[i-1][a+1].getText().equals("FLAG"))
            {
                if(adj[i-1][a+1]==0&&Bfield[i-1][a+1].isEnabled())
                {
                    ;
                }
                else if(adj[i-1][a+1]!=0&&Bfield[i-1][a+1].isEnabled())
                {
                    Bfield[i-1][a+1].setEnabled(false);
                    Bfield[i-1][a+1].setText(""+adj[i-1][a+1]);
                }
            }
        }
        if(i<y-1&&a>0)//Check Down Left
        {
            if(!Bfield[i+1][a-1].getText().equals("FLAG"))
            {
                if(adj[i+1][a-1]==0&&Bfield[i+1][a-1].isEnabled())
                {
                    ;
                }
                else if(adj[i+1][a-1]!=0&&Bfield[i+1][a-1].isEnabled())
                {
                    Bfield[i+1][a-1].setEnabled(false);
                    Bfield[i+1][a-1].setText(""+adj[i+1][a-1]);
                }
            }
        }
        if(i<y-1&&a<x-1)//Check Down Right
        {
            if(!Bfield[i+1][a+1].getText().equals("FLAG"))
            {
                if(adj[i+1][a+1]==0&&Bfield[i+1][a+1].isEnabled())
                {
                    ;
                }
                else if(adj[i+1][a+1]!=0&&Bfield[i+1][a+1].isEnabled())
                {
                    Bfield[i+1][a+1].setEnabled(false);
                    Bfield[i+1][a+1].setText(""+adj[i+1][a+1]);
                }
            }
        }
    }
    
    // - - - - - - - - CONSTRUCTOR! - - - - - - - - - - - - - - - - - - - - - -
    //
    // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    MinesweeperPanel()
    {
        setLayout(new BorderLayout());
        
        // - - - - - Gameplay Icons - - - - - - 
        norm = new ImageIcon("norm.jpg");
        win = new ImageIcon("win.jpg");
        dead = new ImageIcon("ded.jpg");
        mine = new ImageIcon("mine.jpg");
        flag = new ImageIcon("flag.jpg");
        
        face = new JButton(norm);
        face.addActionListener(new ResetHandler());
        add(face, BorderLayout.NORTH);
        mode = new JLabel(mine);
        mode.setFocusable(true);
        mode.addMouseListener(new FlagHandler());
        flags = new JLabel("Mines: XX");
        JPanel south = new JPanel();
        south.add(mode); south.add(flags);
        add(south, BorderLayout.SOUTH);
        // - - - - - - - - - - - - - - - - - - -
        
        // - - - - - - OPTIONS - - - - - - - - -
        while((x<0&&y<0)||custminecount<0)
        {
            try
            {
                JPanel Options = new JPanel();
                JLabel xl = new JLabel("X Size:");
                JLabel yl = new JLabel("Y Size:");
                String[] list = {"2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24","25"};
                JComboBox xbox = new JComboBox(list);
                JComboBox ybox = new JComboBox(list);
                JLabel minel = new JLabel("Mines: ");
                JTextField minet = new JTextField("DEFAULT");
                Options.add(xl);Options.add(xbox);Options.add(yl);Options.add(ybox);
                Options.add(minel);Options.add(minet);
                
                JOptionPane.showConfirmDialog(null,Options);
                x = Integer.parseInt(xbox.getSelectedItem().toString());
                y = Integer.parseInt(ybox.getSelectedItem().toString());
                Bfield = new JButton[y][x];
                adj = new int[y][x];
                if(minet.getText().equalsIgnoreCase("DEFAULT"))
                {
                    custmine = false;
                    custminecount = 999;
                }
                else if(!minet.getText().equalsIgnoreCase("DEFAULT"))
                {
                    custmine = true;
                    int tempcustminecount = Integer.parseInt(minet.getText());
                    if(tempcustminecount<=0||tempcustminecount>(x*y)-1)
                        throw new InputMismatchException();//wrong type, but who wants to make a new exception for this...
                    else custminecount = tempcustminecount;
                }
            }
            catch(InputMismatchException | NumberFormatException | StringIndexOutOfBoundsException e)
            {
                JOptionPane.showMessageDialog(null,"Mines must be an integer less than (total grid area) - 1, and greater than 0. Or DEFAULT for default number");
                
            }
        }
        // - - - - - - - - - - - - - - - - - - - 
        
        
        
        // - - - - - - - - - - - - - - - - - - - 
        //          MINE FIELD
        // - - - - - - - - - - - - - - - - - - -
        field = new JPanel();
        field.setLayout(new GridLayout(x,y));
        ButtonHandler ButtHand = new ButtonHandler();
        for(int i = 0;i<y;i++)
        {
            for(int e = 0; e<x;e++)
            {
                Bfield[i][e] = new JButton("X");
                Bfield[i][e].setBackground(Color.white);
                Bfield[i][e].addActionListener(ButtHand);
                field.add(Bfield[i][e]);
            }
        }
        
        add(field,BorderLayout.CENTER);
        // - - - - - - - - - - - - - - - - - - -
        //
        // - - - - - - - - - - - - - - - - - - -
    }
    
    //Mine Button Handler!
    private class ButtonHandler implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            for(int i = 0;i<y;i++)
            {
                for(int a = 0;a<x;a++)
                {
                    if(e.getSource()==Bfield[i][a])
                    {
                        if(minemode)
                        {
                        // - - - - - - FIRST CLICK SHENANIGANS
                        if(first)//If it's the first click they can't lose immediately! Poor Design
                        {//So we'll instantiate the bomb stuff know, so they are guarenteed safety
                            int left = 0;
                            if(custmine)
                                left = custminecount;
                            else if(!custmine)
                                left = (x*y)/(10)+(x*y*2)/(2*x+2*y);//A reasonable amount that scales higher as the grid increases
                            start = new GregorianCalendar();
                            while(left>0)
                            {
                                int ry =rng.nextInt(y);
                                int rx = rng.nextInt(x);
                                if((i==ry&&a==rx)||(adj[ry][rx]==-1))//If it was the clicked one, or already a mine do nothing
                                    ;
                                else if((i!=ry||a!=rx)&&adj[ry][rx]!=-1) 
                                {
                                    adj[ry][rx]=-1;//-1 being a bomb
                                    left--;
                                }//Else reduce the counter, we good bois!
                            }
                            flagcount = 0;
                            for(int f = 0; f<y;f++)
                            {
                                for(int g = 0; g<x;g++)
                                {
                                    if(adj[f][g]==-1) flagcount++;
                                }
                            }
                            minesleft = flagcount;
                            flags.setText("Mines: "+flagcount);
                            
                            //Adjancies
                            for(int f = 0;f<y;f++)
                            {
                                for(int g = 0;g<x;g++)
                                {//Adds up number of mines adjacent to tile
                                    if(adj[f][g]==-1);//If it is a mine, skip
                                    else if (adj[f][g]!=-1)
                                    {
                                        if(f>0)//Check above
                                            {if(adj[f-1][g]==-1) adj[f][g]+=1;}
                                        if(f<y-1)//Check Below
                                            {if(adj[f+1][g]==-1) adj[f][g]+=1;}
                                        if(g>0)//Check Left
                                            {if(adj[f][g-1]==-1) adj[f][g]+=1;}
                                        if(g<x-1)//Check Right
                                            {if(adj[f][g+1]==-1) adj[f][g]+=1;}
                                        if(f>0&&g>0)//Check UP LEFT
                                            {if(adj[f-1][g-1]==-1) adj[f][g]+=1;}
                                        if(f>0&&g<x-1)//Check UP RIGHT
                                            {if(adj[f-1][g+1]==-1) adj[f][g]+=1;}
                                        if(f<y-1&&g>0)//Check DOWN LEFT
                                            {if(adj[f+1][g-1]==-1) adj[f][g]+=1;}
                                        if(f<y-1&&g<x-1)//Check DOWN RIGHT
                                            {if(adj[f+1][g+1]==-1) adj[f][g]+=1;}
                                    }
                                }
                            }
                        first = false;//Forgot this at first, 20 mines would appear in a 3x3 grid...
                        }//END OF FIRST - - - - - - - - - - - - - - - - - - -
                        
                        Bfield[i][a].setEnabled(false);
                        switch(adj[i][a])
                        {
                            case 0:
                            {Bfield[i][a].setText("");break;}
                            case 1:
                            {Bfield[i][a].setText(""+adj[i][a]);break;}
                            case 2:
                            {Bfield[i][a].setText(""+adj[i][a]);break;}
                            case 3:
                            {Bfield[i][a].setText(""+adj[i][a]);break;}
                            case 4:
                            {Bfield[i][a].setText(""+adj[i][a]);break;}
                            case 5:
                            {Bfield[i][a].setText(""+adj[i][a]);break;}
                            case 6:
                            {Bfield[i][a].setText(""+adj[i][a]);break;}
                            case 7:
                            {Bfield[i][a].setText(""+adj[i][a]);break;}
                            case 8:
                            {Bfield[i][a].setText(""+adj[i][a]);break;}
                            case -1:
                            {Bfield[i][a].setText("MINE");Bfield[i][a].setBackground(new Color(230,225,10));break;}
                        }
                        if(adj[i][a]==-1)//If it's a mine, die
                        {
                            face.setIcon(dead);
                            for(int f = 0;f<y;f++)//Reveal the board
                            {
                                for(int g = 0;g<x;g++)
                                {
                                    if(Bfield[f][g].isEnabled())
                                    {
                                        switch(adj[f][g])
                                        {
                                            case 0:
                                            {Bfield[f][g].setText("");break;}
                                            case 1:
                                            {Bfield[f][g].setText(""+adj[f][g]);break;}
                                            case 2:
                                            {Bfield[f][g].setText(""+adj[f][g]);break;}
                                            case 3:
                                            {Bfield[f][g].setText(""+adj[f][g]);break;}
                                            case 4:
                                            {Bfield[f][g].setText(""+adj[f][g]);break;}
                                            case 5:
                                            {Bfield[f][g].setText(""+adj[f][g]);break;}
                                            case 6:
                                            {Bfield[f][g].setText(""+adj[f][g]);break;}
                                            case 7:
                                            {Bfield[f][g].setText(""+adj[f][g]);break;}
                                            case 8:
                                            {Bfield[f][g].setText(""+adj[f][g]);break;}
                                            case -1:
                                            {Bfield[f][g].setText("MINE");Bfield[f][g].setBackground(new Color(220,10,10));break;}
                                        }
                                        Bfield[f][g].setEnabled(false);
                                    }
                                }
                            }
                        }
                        //If it's a clear space, clear everywhere to the next numbered spaces
                        else if(adj[i][a]==0) ZeroClear(i,a,x,y,Bfield,adj);
                        
                        //If there are no empty spaces left, win!
                        int count = 0;
                        for(int f = 0; f<y;f++)
                        {
                            for(int g = 0;g<x;g++)
                            {
                                if(Bfield[f][g].isEnabled())
                                    count++;
                            }
                        }
                        if(count==minesleft)//If equal
                        {
                            face.setIcon(win);
                            
                            end = new GregorianCalendar();// SCORE BOARD STUFF - - - - 
                            int timescore = (int) end.getTimeInMillis()/1000 - (int) start.getTimeInMillis()/1000;
                            if(JOptionPane.showConfirmDialog(null, "Save score "+timescore+" seconds?")==0)
                            {
                                String name = JOptionPane.showInputDialog("Enter your name!");
                                try
                                {
                                    scorein = new Scanner(scores);
                                    String scoresshown ="";
                                    while(scorein.hasNextLine())
                                    {
                                        scoresshown += scorein.nextLine()+"\n";
                                    }
                                    scoreout = new PrintStream(scores);
                                    scoresshown+="\n"+timescore+"s "+minesleft+" Mines "+ x+"x"+y+" "+name;
                                    scoreout.println(scoresshown);
                                    scorein.close();scoreout.close();
                                    JOptionPane.showMessageDialog(null,scoresshown);
                                }
                                catch(IOException dad)
                                {
                                    JOptionPane.showMessageDialog(null, "Score table not found, try again later");
                                }
                                
                            }//SCore board end
                            
                            for(int f = 0;f<y;f++)
                            {
                                for(int g = 0;g<x;g++)
                                {
                                    if(Bfield[f][g].isEnabled())
                                    {
                                        switch(adj[f][g])
                                        {
                                            case 0:
                                            {Bfield[f][g].setText("");break;}
                                            case 1:
                                            {Bfield[f][g].setText(""+adj[f][g]);break;}
                                            case 2:
                                            {Bfield[f][g].setText(""+adj[f][g]);break;}
                                            case 3:
                                            {Bfield[f][g].setText(""+adj[f][g]);break;}
                                            case 4:
                                            {Bfield[f][g].setText(""+adj[f][g]);break;}
                                            case 5:
                                            {Bfield[f][g].setText(""+adj[f][g]);break;}
                                            case 6:
                                            {Bfield[f][g].setText(""+adj[f][g]);break;}
                                            case 7:
                                            {Bfield[f][g].setText(""+adj[f][g]);break;}
                                            case 8:
                                            {Bfield[f][g].setText(""+adj[f][g]);break;}
                                            case -1:
                                            {Bfield[f][g].setText("MINE");break;}
                                        }
                                        Bfield[f][g].setEnabled(false);
                                    }
                                }
                            }
                        }
                        }
                        else if (!minemode)
                        {
                            if(Bfield[i][a].getText().equals("FLAG"))
                            {
                                Bfield[i][a].setText("X");
                                Bfield[i][a].setBackground(Color.white);
                                flagcount++;
                                flags.setText("Mines: "+flagcount);
                            }
                            else if(!Bfield[i][a].getText().equals("FLAG"))
                            {
                                Bfield[i][a].setText("FLAG");
                                Bfield[i][a].setBackground(new Color(200,200,255));
                                flagcount--;
                                flags.setText("Mines: "+flagcount);
                            }
                        }
                    } 
                }
            }
              
        }
    }
    
    //Reset Button Handler
    private class ResetHandler implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            if(e.getSource()==face)
            {
                flags.setText("Mines: XX");
                face.setIcon(norm);
                first = true;
                for(int i = 0;i<y;i++)//Clears int array
                {
                    for(int a = 0;a<x;a++)
                    {
                        adj[i][a]=0;
                    }
                }
                for(int i = 0;i<y;i++)//Clears JButton array
                {
                    for(int a = 0;a<x;a++)
                    {
                        Bfield[i][a].setEnabled(true);
                        Bfield[i][a].setText("X");
                        Bfield[i][a].setBackground(Color.white);
                    }
                }
            }
        }
    }
    //Change click modes
    private class FlagHandler implements MouseListener
    {
        @Override
        public void mouseClicked(MouseEvent e)
        {
            if(minemode)
            {
                minemode = false;
                mode.setIcon(flag);
            }
            else if (!minemode)
            {
                minemode = true;
                mode.setIcon(mine);
            }
        }
        @Override
        public void mousePressed(MouseEvent e){}
        @Override
        public void mouseReleased(MouseEvent e){}
        @Override
        public void mouseEntered(MouseEvent e){}
        @Override
        public void mouseExited(MouseEvent e){}
    }
    
}
