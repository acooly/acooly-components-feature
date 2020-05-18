#!/usr/bin/env python
#coding=utf-8

from aliyunsdkcore.client import AcsClient
from aliyunsdkcore.request import CommonRequest
client = AcsClient('AK', 'SK', 'default')

request = CommonRequest()
request.set_accept_format('json')
request.set_domain('dysmsapi.aliyuncs.com')
request.set_method('POST')
request.set_protocol_type('https') # https | http
request.set_version('2017-05-25')
request.set_action_name('SendSms')

request.add_query_param('PhoneNumbers', "13820000000")
request.add_query_param('SignName', "云顶云")
request.add_query_param('TemplateCode', "SMS_64685056")
request.add_query_param('TemplateParam', "{\"content\":\"测试\"}")

response = client.do_action(request)
# python2:  print(response) 
print(str(response, encoding = 'utf-8'))
