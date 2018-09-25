<#if Application.xxx?exists>
    ${Application.xxx}
</#if>

<h1>${message},${name}</h1>

<h2>requestUri:${rc.requestUri}</h2>
<h2>queryString:${rc.queryString}</h2>

<h2>session attribute:${Session.valueInSession}</h2>

<h2>request parameter:${RequestParameters.key}</h2>

<h2>request atrribute:${valueInRequest}</h2>

