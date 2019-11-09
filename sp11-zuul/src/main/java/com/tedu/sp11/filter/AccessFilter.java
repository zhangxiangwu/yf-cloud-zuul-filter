package com.tedu.sp11.filter;

import javax.servlet.http.HttpServletRequest;

import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import com.tedu.web.util.JsonResult;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class AccessFilter extends ZuulFilter{

	@Override
	public boolean shouldFilter() {
		// TODO Auto-generated method stub
		RequestContext ctx = RequestContext.getCurrentContext();
		
		String serviceId = (String) ctx.get(FilterConstants.SERVICE_ID_KEY);
		
		if(serviceId.equalsIgnoreCase("item-service")) {
			return true;
		}
		
		return false;
	}

	@Override
	public Object run() throws ZuulException {
		// TODO Auto-generated method stub
	   RequestContext ctx = RequestContext.getCurrentContext();
	   
	   HttpServletRequest req = ctx.getRequest();
	   
	   String at = req.getParameter("token");
	   
	   if(at == null) {
		   ctx.setSendZuulResponse(false);
		   ctx.setResponseStatusCode(200);
		   
		   ctx.setResponseBody(JsonResult.err().code(JsonResult.NOT_LOGIN).toString());
	   }
	   
		return null;
	}

	@Override
	public String filterType() {
		return FilterConstants.PRE_TYPE;
	}

	@Override
	public int filterOrder() {
		return FilterConstants.PRE_DECORATION_FILTER_ORDER+1;
	}

}



























