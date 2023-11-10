/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ReCode_Sever;

/**
 *
 * @author Administrator
 */
public class myAdmin 
{
    public static short[] ListLechHEAD = new short[]
	{
		719,
		748,
		751,
		756,
		798,
		799,
		801,
		802,
		849,
		851,
		894,
		896,
		950,
		963,
		972
	};
    
    public static long maxDame(long dame)
    {
        if(dame < 0)
        {
            dame = 99;
        }
        return dame > 500000000 ? 500000000 : dame;
    }
}
