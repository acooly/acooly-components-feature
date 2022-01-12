<div>
    <div class="card-header">
        <h3 class="card-title">${emailRecord.subject}</h3>
    </div>
    <!-- /.card-header -->
    <div class="card-body p-0">
        <div class="mailbox-read-info" style="padding-left: 1.25rem;">
            <h6>${emailRecord.fromAddress} <span class="mailbox-read-time float-right">${emailRecord.createTime?string('yyyy-MM-dd HH:mm:ss')}</span></h6>
        </div>
        <!-- /.mailbox-controls -->
        <div class="mailbox-read-message">
            ${emailRecord.content}
        </div>
        <!-- /.mailbox-read-message -->
    </div>
</div>

