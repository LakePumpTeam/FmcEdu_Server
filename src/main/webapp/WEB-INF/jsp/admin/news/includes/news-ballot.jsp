<div id="news_content">
    <form action="${ctx}/admin/news/publishNews" method="post" enctype="multipart/form-data" id="news_form">
        <input type="hidden" id="newsType" name="newsType" value="7" />

        <div class="form-group"><label for="subject">标题:</label>
            <input type="text" id="subject" name="subject" style="width:750px" />
        </div>
        <script type="text/plain" id="myEditor" name="content" style="width:100%;height:240px;"><p>输入校园吧信息...</p></script>
        <br />

        <div style="float: right;">
            <div class="form-group">
                <label for="option1">选项 #1:</label>
                <input type="text" name="selections" style="width:300px;" id="option1" />
                <input type="button" class="btn_clear" value="清空" />
            </div>
            <div class="form-group">
                <label for="option2">选项 #2:</label>
                <input type="text" name="selections" style="width:300px;" id="option2" />
                <input type="button" class="btn_clear" value="清空" />
            </div>
            <div class="form-group">
                <label for="option3">选项 #3:</label>
                <input type="text" name="selections" style="width:300px;" id="option3" />
                <input type="button" class="btn_clear" value="清空" />
            </div>
            <div class="form-group">
                <label for="option4">选项 #4:</label>
                <input type="text" name="selections" style="width:300px;" id="option4" />
                <input type="button" class="btn_clear" value="清空" />
            </div>
            <div class="form-group">
                <label for="option5">选项 #5:</label>
                <input type="text" name="selections" style="width:300px;" id="option5" />
                <input type="button" class="btn_clear" value="清空" />
            </div>
            <div class="form-group">
                <label for="option6">选项 #6:</label>
                <input type="text" name="selections" style="width:300px;" id="option6" />
                <input type="button" class="btn_clear" value="清空" />
            </div>
            <div class="form-group">
                <label for="option7">选项 #7:</label>
                <input type="text" name="selections" style="width:300px;" id="option7" />
                <input type="button" class="btn_clear" value="清空" />
            </div>
        </div>

        <div class="form-group">
            <label for="img1">上传图片 #1:</label>
            <input type="file" name="imgs" id="img1" />
        </div>
        <div class="form-group">
            <label for="img2">上传图片 #2:</label>
            <input type="file" name="imgs" id="img2" />
        </div>
        <div class="form-group">
            <label for="img3">上传图片 #3:</label>
            <input type="file" name="imgs" id="img3" />
        </div>
        <div class="form-group">
            <label for="img4">上传图片 #4:</label>
            <input type="file" name="imgs" id="img4" />
        </div>
        <br />
        <input type="submit" value="发布" id="submit" class="btn btn btn-primary" />
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