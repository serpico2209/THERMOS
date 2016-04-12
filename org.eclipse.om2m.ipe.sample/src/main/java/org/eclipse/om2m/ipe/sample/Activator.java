/*******************************************************************************
 * Copyright (c) 2013-2016 LAAS-CNRS (www.laas.fr)
 * 7 Colonel Roche 31077 Toulouse - France
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Initial Contributors:
 *     Thierry Monteil : Project manager, technical co-manager
 *     Mahdi Ben Alaya : Technical co-manager
 *     Samir Medjiah : Technical co-manager
 *     Khalil Drira : Strategy expert
 *     Guillaume Garzone : Developer
 *     François Aïssaoui : Developer
 *
 * New contributors :
 *******************************************************************************/
package org.eclipse.om2m.ipe.sample;
<<<<<<< HEAD
//Test push serveur test nouvelle branche local
=======

>>>>>>> master
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.om2m.core.service.CseService;
import org.eclipse.om2m.interworking.service.InterworkingService;
import org.eclipse.om2m.ipe.sample.controller.LifeCycleManager;
<<<<<<< HEAD
import org.eclipse.om2m.ipe.sample.controller.ThermosController;
=======
import org.eclipse.om2m.ipe.sample.controller.SampleController;
>>>>>>> master
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTracker;
/**
 *  Manages the starting and stopping of the bundle.
 */
public class Activator implements BundleActivator {
    /** Logger */
    private static Log logger = LogFactory.getLog(Activator.class);
    /** SCL service tracker */
    private ServiceTracker<Object, Object> cseServiceTracker;

    @Override
    public void start(BundleContext bundleContext) throws Exception {
        logger.info("Register IpeService..");
<<<<<<< HEAD
        bundleContext.registerService(InterworkingService.class.getName(), new ThermosRouter(), null);
=======
        bundleContext.registerService(InterworkingService.class.getName(), new SampleRouter(), null);
>>>>>>> master
        logger.info("IpeService is registered.");

        cseServiceTracker = new ServiceTracker<Object, Object>(bundleContext, CseService.class.getName(), null) {
            public void removedService(ServiceReference<Object> reference, Object service) {
                logger.info("CseService removed");
            }

            public Object addingService(ServiceReference<Object> reference) {
                logger.info("CseService discovered");
                CseService cseService = (CseService) this.context.getService(reference);
<<<<<<< HEAD
                ThermosController.setCse(cseService);
=======
                SampleController.setCse(cseService);
>>>>>>> master
                new Thread(){
                    public void run(){
                        try {
                        	LifeCycleManager.start();
                        } catch (Exception e) {
                            logger.error("IpeMonitor Sample error", e);
                        }
                    }
                }.start();
                return cseService;
            }
        };
        cseServiceTracker.open();
    }

    @Override
    public void stop(BundleContext bundleContext) throws Exception {
        logger.info("Stop Ipe Sample");
        try {
        	LifeCycleManager.stop();
        } catch (Exception e) {
            logger.error("Stop IPE Sample error", e);
        }
    }

}
