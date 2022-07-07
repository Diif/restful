/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationContextFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rest.model.ShopUnitImport;
import rest.model.ShopUnitType;
import rest.repositories.ShopUnitImportRepository;

import javax.validation.Valid;
import java.net.http.HttpResponse;
import java.util.Optional;
import java.util.UUID;

@SpringBootApplication
@RestController
public class App {
    private ShopUnitImportRepository repo;

    public ShopUnitImportRepository getRepo() {
        return repo;
    }

    @Autowired
    public void setRepo(ShopUnitImportRepository repo) {
        this.repo = repo;
    }

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

//    @PostMapping("/imports")
//    public ResponseEntity<ShopUnitImport> sayHello(@Valid @RequestBody ShopUnitImport shopUnitImport){
//        Optional<ShopUnitImport> optional =  repo.findById(shopUnitImport.getId());
//        if(optional.isPresent()){
//            if(optional.get().getType() != shopUnitImport.getType()){
//                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//            }
//        }
//        repo.save(shopUnitImport);
//        return new ResponseEntity<>(shopUnitImport,HttpStatus.OK);
//    }
}