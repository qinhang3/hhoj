<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <title>A Simple Page with CKEditor</title>
        <!-- Make sure the path to CKEditor is correct. -->
        <script src="../ckeditor/ckeditor.js"></script>
    </head>
    <body>
        <form>
            <textarea name="editor1" id="editor1" rows="20" cols="80">
                This is my textarea to be replaced with CKEditor.
            </textarea>
            <script>
                // Replace the <textarea id="editor1"> with a CKEditor
                // instance, using default configuration.
                CKEDITOR.replace( 'editor1' );
            </script>
            <button value="submit" >SUBMIT</button>
        </form>
        
        <div id="editor2" contenteditable="true">
		    <h1>Inline Editing in Action!</h1>
		    <p>The "div" element that contains this text is now editable.
		</div>
		<script>
		    // Turn off automatic editor creation first.
		    CKEDITOR.disableAutoInline = true;
		    CKEDITOR.inline( 'editor2' );
		</script>
    </body>
</html>