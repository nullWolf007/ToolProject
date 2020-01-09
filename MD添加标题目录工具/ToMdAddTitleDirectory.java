import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.util.List;

/*正确的格式
- <!-- TOC -->

  - [一 OSI与TCP/IP各层的结构与功能,都有哪些协议?](#一-osi与tcpip各层的结构与功能都有哪些协议)
      - [1.1 应用层](#11-应用层)
      - [1.2 运输层](#12-运输层)
      - [1.3 网络层](#13-网络层)
      - [1.4 数据链路层](#14-数据链路层)
      - [1.5 物理层](#15-物理层)
      - [1.6 总结一下](#16-总结一下)
  - [二 TCP 三次握手和四次挥手(面试常客)](#二-tcp-三次握手和四次挥手面试常客)
      - [2.1 TCP 三次握手漫画图解](#21-tcp-三次握手漫画图解)
      - [2.2 为什么要三次握手](#22-为什么要三次握手)
      - [2.3 为什么要传回 SYN](#23-为什么要传回-syn)
      - [2.4 传了 SYN,为啥还要传 ACK](#24-传了-syn为啥还要传-ack)
      - [2.5 为什么要四次挥手](#25-为什么要四次挥手)
  - [三 TCP,UDP 协议的区别](#三-tcpudp-协议的区别)
  - [四 TCP 协议如何保证可靠传输](#四-tcp-协议如何保证可靠传输)
      - [4.1 ARQ协议](#41-arq协议)
          - [停止等待ARQ协议](#停止等待arq协议)
          - [连续ARQ协议](#连续arq协议)
      - [4.2 滑动窗口和流量控制](#42-滑动窗口和流量控制)
      - [4.3 拥塞控制](#43-拥塞控制)
  - [五  在浏览器中输入url地址 ->> 显示主页的过程(面试常客)](#五--在浏览器中输入url地址---显示主页的过程面试常客)
  - [六 状态码](#六-状态码)
  - [七 各种协议与HTTP协议之间的关系](#七-各种协议与http协议之间的关系)
  - [八  HTTP长连接,短连接](#八--http长连接短连接)
  - [九 HTTP是不保存状态的协议,如何保存用户状态?](#九-http是不保存状态的协议如何保存用户状态)
  - [十 Cookie的作用是什么?和Session有什么区别？](#十-cookie的作用是什么和session有什么区别)
  - [十一 HTTP 1.0和HTTP 1.1的主要区别是什么?](#十一-http-10和http-11的主要区别是什么)
  - [十二 URI和URL的区别是什么?](#十二-uri和url的区别是什么)
  - [十三 HTTP 和 HTTPS 的区别？](#十三-http-和-https-的区别)
  - [建议](#建议)
  - [参考](#参考)

  <!-- /TOC -->
 */

public class ToMdAddTitleDirectory extends Application {

    @Override
    public void start(final Stage stage) {
        stage.setTitle("MD File Chooser ");

        final FileChooser fileChooser = new FileChooser();

        final Button selectMdsButton = new Button("Open mds...");
        selectMdsButton.setMinWidth(300);
        selectMdsButton.setMinHeight(50);

        selectMdsButton.setOnAction(
                (final ActionEvent e) -> {
                    List<File> list =
                            fileChooser.showOpenMultipleDialog(stage);
                    if (list != null) {
                        list.stream().forEach((file) -> {
                            addTitleDirectory(file);
                        });
                    }
                });

        final GridPane inputGridPane = new GridPane();

        GridPane.setConstraints(selectMdsButton, 1, 0);
        inputGridPane.setHgap(12);
        inputGridPane.setVgap(12);
        inputGridPane.getChildren().addAll(selectMdsButton);

        final Pane rootGroup = new VBox(12);
        rootGroup.getChildren().addAll(inputGridPane);
        rootGroup.setPadding(new Insets(12, 12, 12, 12));

        stage.setScene(new Scene(rootGroup));
        stage.show();
    }

    public static void main(String[] args) {
        Application.launch(args);
    }

    //对文件进行添加头
    private void addTitleDirectory(File file) {
        String titleStr = "[TOC]\n- <!-- TOC -->\n";

        //读文件
        try {
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String currentLineStr = "";
            int[] dp = new int[4];//用来计数 需要空格多少行
            while ((currentLineStr = bufferedReader.readLine()) != null) //●判断最后一行不存在，为空
            {
                int count = 0;
                if (currentLineStr.contains("##### ") || currentLineStr.contains("#### ") || currentLineStr.contains("### ")
                        || currentLineStr.contains("## ") || currentLineStr.contains("# ")) {
                    String replaceLineStr = currentLineStr.trim().replace("#", "");
                    String regNo = "[#|?|(|)|.|,|，|、|:|？|（|）|。|=|+|/|：]";
                    String regVerLine = "[ |>|<]";
                    String specialStr = "";
                    if (currentLineStr.contains("##### ")) {//五级标题
                        count = dp[3] = dp[2] + 2;
                    } else if (currentLineStr.contains("#### ")) {//四级标题
                        count = dp[2] = dp[1] + 2;
                    } else if (currentLineStr.contains("### ")) {//三级标题
                        count = dp[1] = dp[0] + 2;
                    } else if (currentLineStr.contains("## ")) {//二级标题
                        dp = new int[4];
                        count = dp[0] = 2;
                    } else if (currentLineStr.contains("# ")) {//一级标题
                        dp = new int[4];
                        count = 0;
                    }
                    for (int i = 0; i < count; i++) {
                        specialStr += " ";
                    }
                    specialStr += "- [";
                    titleStr = titleStr
                            + specialStr
                            + replaceLineStr
                            + "](#"
                            + currentLineStr.replaceAll(regNo, "").trim().replaceAll(regVerLine, "-")
                            + ")\n";
                }
            }
            bufferedReader.close();
            fileReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        titleStr = titleStr + "  <!-- /TOC -->\n";
        System.out.println(titleStr);

        //写文件 追加的形式写到文件开始位置
        try {
            byte[] titleBytes = titleStr.getBytes();
            RandomAccessFile src = new RandomAccessFile(file, "rw");
            int srcLength = (int) src.length();
            byte[] buff = new byte[srcLength];
            src.read(buff, 0, srcLength);
            src.seek(0);
            src.write(titleBytes);
            src.seek(titleBytes.length);
            src.write(buff);
            src.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
