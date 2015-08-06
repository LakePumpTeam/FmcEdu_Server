<div class="form-group input-group">
    <span class="input-group-addon">标题：</span>
    <input type="text" id="subject" name="subject" class="form-control" />
</div>
<input type="hidden" id="newsType" name="newsType" value="${param.mode}" />
<script type="text/plain" id="myEditor" name="content" style="width:100%;height:240px;"><p>输入亲子教育信息...</p></script>
<br />

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
<div class="form-group">
    <input type="submit" value="发布" id="submit" class="btn btn btn-primary" />
</div>
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