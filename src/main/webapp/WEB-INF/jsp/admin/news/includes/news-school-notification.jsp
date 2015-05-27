<div id="news_content">
    <div id="editor">
        <script type="text/plain" id="myEditor" style="width:100%;height:240px;">
        <p>输入校园通知信息...</p>


        </script>

        <!--ueditor-->
        <script type="text/javascript">
            var um = UM.getEditor('myEditor', {
                //这里可以选择自己需要的工具按钮名称,此处仅选择如下七个
                toolbar: ['fullscreen undo redo'],
                //focus时自动清空初始化时的内容
                autoClearinitialContent: true,
                wordCount: false,
                //关闭elementPath
                elementPathEnabled: false,
                //默认的编辑区域高度
                initialFrameHeight: 300
                //更多其他参数，请参考umeditor.config.js中的配置项
            });
        </script>
    </div>
    <div>
        <form action="#" method="post" enctype="multipart/form-data" id="news_form">
            <input type="hidden" id="newsType" name="newsType" value=""/>
            <input type="hidden" id="newsContent" name="content" value=""/>
            <input type="submit" value="发布" id="submit" class="btn btn-warning btn-sm pull-right"/>
        </form>
    </div>
</div>