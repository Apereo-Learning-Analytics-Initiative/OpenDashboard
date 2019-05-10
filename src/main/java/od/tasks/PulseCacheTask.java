package od.tasks;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import od.framework.api.PulseController;
import od.framework.model.Tenant;
import od.providers.ProviderData;
import od.providers.ProviderException;
import od.providers.ProviderService;
import od.providers.config.ProviderDataConfigurationException;
import od.providers.enrollment.EnrollmentProvider;
import od.repository.mongo.MongoTenantRepository;

@Component
@Service
public class PulseCacheTask {

    @Autowired PulseCacheService pulseCacheService;
    @Autowired private ProviderService providerService;
    @Autowired private MongoTenantRepository mongoTenantRepository;
    
    //scheduled every 12 hours
    @Scheduled(fixedRate = 12 * 60 * 60 * 1000)
    public void updatePulseCache() {
      
      System.out.println("Scheduler is running");
      List<Tenant> tenants = mongoTenantRepository.findAll();
      
      for(Tenant tenant: tenants) {
        ProviderData rosterProviderData;
        try {
          rosterProviderData = providerService.getConfiguredProviderDataByType(tenant, ProviderService.ROSTER);
        
          EnrollmentProvider enrollmentProvider = providerService.getRosterProvider(tenant);
          List<String> teacherIds = enrollmentProvider.getUniqueUsersWithRole(rosterProviderData, "teacher");
          if (teacherIds != null) {
            System.out.println("Cacheing pulsedetails for " + teacherIds.size() + " teachers");
          }
          
          //since this is a multi-server environment
          //let's randomize the order in which we update. 
          //This will make it far less likely that we step on each other's toes
          /*while(teacherIds.size()>0) {
            int index = new Random().nextInt(teacherIds.size());
            String userId = teacherIds.get(index);
            syncPulse(tenant.getId(),userId);
            teacherIds.remove(index);
          }*/
          syncPulse(tenant.getId(),"tmp00456@ncsu.edu");
        } catch (ProviderDataConfigurationException e) {
          e.printStackTrace();
        } catch (ProviderException e) {
          System.out.println(e);
          e.printStackTrace();
        } catch (InterruptedException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
      }           
    }
    
    
    public CompletableFuture<String> syncPulse(String tenantId, String userId)  throws InterruptedException {
      System.out.println("starting for userid: " + userId);
      List<CompletableFuture<String>> results = new ArrayList<>();
      try {
        results.add(pulseCacheService.pulseCache(tenantId, userId));
      } catch (ProviderDataConfigurationException | ProviderException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }      
      System.out.println("userid on The Queue: " + userId);
      return CompletableFuture.completedFuture("done");
    }
}