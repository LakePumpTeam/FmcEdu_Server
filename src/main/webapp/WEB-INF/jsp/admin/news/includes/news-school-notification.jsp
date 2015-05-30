<div id="news_content">
    <form action="${ctx}/admin/news/publishNews" method="post" enctype="multipart/form-data" id="news_form">
        <input type="hidden" id="newsType" name="newsType" value="3"/>

        <div class="form-group"><label for="subject">标题:</label>
            <input type="text" id="subject" name="subject" style="width:100%"/>
        </div>
        <script type="text/plain" id="myEditor" name="content" style="width:100%;height:240px;"><p>输入校园通知信息...</p>
        </script>
        <input type="submit" value="发布" id="submit" class="btn btn btn-primary"/>
    </form>
    <script type="text/javascript">
        var um = UM.getEditor('myEditor', {
            toolbar: ['fullscreen undo redo'],
            //clear content during focus
            autoClearinitialContent: true,
            wordCount: false,
            //disable element path
            elementPathEnabled: false,
            // default edit area height
            initialFrameHeight: 300
        });
    </script>
</div>