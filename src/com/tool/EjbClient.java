package com.tool;

import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class EjbClient<T> {

	public EjbClient() {

	}

	/**
	 * for remote/http/inTomee
	 * @param The bean name without remote or local in the tail.
	 * @param hosts specify hosts that can service ejb client, the hosts are seperate by a symbol called common (,) .
	 * @return
	 * @throws NamingException
	 */
	static public Object defaultTomeeRemoteBeanFactory(String beanName, String hosts) throws NamingException {
		EjbClient<?> ec = new EjbClient<>();
		InitialContext ctx = ec.getContext(true, true, hosts, 8080, true);
		return ctx.lookup(beanName + "Remote");
	}
	
	static public <I> I factory (String ejbBeanName) throws NamingException {
		EjbClient<I> ejb = new EjbClient<I>();
		return ejb.invokeRemoteOpenEjb(ejbBeanName, true, true, "127.0.0.1", 8080, true);	
	}	

	@SuppressWarnings("unchecked")
	public T invokeRemoteOpenEjb(String beanName, Boolean remote, Boolean useHttp, String hostNameOrIp, int port,
			Boolean inTomee) throws NamingException {
		InitialContext ctx = getContext(remote, useHttp, hostNameOrIp, port, inTomee);
		T ejbBean = null;
		try {
			ejbBean = (T) ctx.lookup(beanName + "Remote");
		} catch (Exception e) {
			throw e;
		}
		return ejbBean;
	}

	private InitialContext getContext(Boolean remote, Boolean useHttp, String hostNameOrIp, int port, Boolean inTomee)
			throws NamingException {
		Properties p = new Properties();
		if (remote) {
			p.put(Context.INITIAL_CONTEXT_FACTORY, "org.apache.openejb.client.RemoteInitialContextFactory");
			if (useHttp) {
				if (inTomee) {
					// Remote Client with HTTP (in TomEE)
					p.put(Context.PROVIDER_URL,
							"http://" + hostNameOrIp + ":" + ((port == 0) ? 8080 : port) + "/tomee/ejb");

				} else {
					// Remote Client with HTTP (openejb standalone)
					p.put(Context.PROVIDER_URL, "http://" + hostNameOrIp + ":" + ((port == 0) ? 4201 : port) + "/ejb");
				}
			} else {
				// Remote Client (openejb standalone)
				p.put(Context.PROVIDER_URL, "ejbd://" + hostNameOrIp + ":" + ((port == 0) ? 4201 : port));
			}

		} else
			p.put(Context.INITIAL_CONTEXT_FACTORY, "org.apache.openejb.client.LocalInitialContextFactory");

		// user and pass optional
		// p.put("java.naming.security.principal", "myuser");
		// p.put("java.naming.security.credentials", "mypass");

		InitialContext ctx = new InitialContext(p);
		return ctx;
	}

}
