package cn.hgxsp.hegangxsp.utils;

/**
 * DESC：FFmpeg 测试工具类
 * CREATED BY ：@hou.linan
 * CREATED DATE ：2018/8/6
 * Time : 9:39
 */
public class FfmpegUtilTest {

    //执行文件位置
    private String ffmpegExe ;

    public FfmpegUtilTest(String ffmpegExe ){
        super();
        this.ffmpegExe = ffmpegExe ;
    }

    /*
    *DESC:转换测试方法
    *@author hou.linan
    *@date:  2018/8/6 9:43
    *@param:
    *@return:
    * ** ffmpeg -i input.mp4 output.avi
    */
//    public void convertor(String videoInputPath , String  videoOutPutPath) throws Exception{
//
//        List<String> command = new ArrayList<>();
//        command.add(ffmpegExe);
//        command.add("-i");
//        command.add(videoInputPath);
//        command.add(videoOutPutPath) ;
//
//
//        ProcessBuilder builder =  new ProcessBuilder(command);
//        Process process = builder.start();
//        //获取流 ，这个ErrorStream  就是inputStream流
//        InputStream errorStream = process.getErrorStream();
//        InputStreamReader inputStreamReader = new InputStreamReader( errorStream);
//        BufferedReader bufferedReader = new BufferedReader(inputStreamReader) ;
//
//        String line = "";
//        while((line = bufferedReader.readLine()) != null );
//
//        //关闭br
//        if(bufferedReader != null ) bufferedReader.close();
//        if(inputStreamReader!= null ) inputStreamReader.close();
//        if(errorStream!=null ) errorStream.close();
//
//
//
//    }


    public static void main(String[] args) {
//
//        FfmpegUtilTest  ff = new FfmpegUtilTest("G:\\JAVA\\ffmpeg\\bin\\ffmpeg.exe");
//        try {
//            ff.convertor("G:\\JAVA\\ffmpeg\\wx123.mp4" , "G:\\JAVA\\ffmpeg\\wx1234.avi");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }


    }
}
