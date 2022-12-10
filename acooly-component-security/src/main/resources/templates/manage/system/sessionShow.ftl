<div class="card-body">
    <dl class="row">
        <dt class="col-sm-3">id:</dt>
        <dd class="col-sm-9">${session.id}</dd>
        <dt class="col-sm-3">开始时间:</dt>
        <dd class="col-sm-9">${(session.startTimestamp?string('yyyy-MM-dd HH:mm:ss'))!}</dd>
        <dt class="col-sm-3">最后访问时间:</dt>
        <dd class="col-sm-9">${(session.lastAccessTime?string('yyyy-MM-dd HH:mm:ss'))!}</dd>
        <dt class="col-sm-3">有效时长（秒）:</dt>
        <dd class="col-sm-9">${session.timeout/1000}</dd>
        <dt class="col-sm-3">域名:</dt>
        <dd class="col-sm-9">${session.host}</dd>
        <dt class="col-sm-3">结束时间:</dt>
        <dd class="col-sm-9">${(session.stopTimestamp?string('yyyy-MM-dd HH:mm:ss'))!}</dd>
        <dt class="col-sm-3">占用空间大小(K):</dt>
        <dd class="col-sm-9">${(sessionLength/1024)?string('#.##')}</dd>
    </dl>

    <h6>Attributes：</h6>
    <dl class="row">
        <#list session.attributes as k,v>
            <dt class="col-sm-8">${k}:</dt>
            <dd class="col-sm-4">${v}</dd>
        </#list>
    </dl>
</div>
