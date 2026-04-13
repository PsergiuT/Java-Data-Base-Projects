package Service;

import Repo.RepoDB;

public class ServiceApp {
    private final RepoDB repo;

    public ServiceApp(RepoDB repo){
        this.repo = repo;
    }

    public void Nplus1Lazy(){
        repo.Nplus1Lazy();
    }

    public void Nplus1Eager(){
        repo.Nplus1Eager();
    }

    public void BenchmarkWithoutIndex(){
        repo.BenchmarkWithoutIndex();
    }


    public void BenchmarkIndex(){
        repo.BenchmarkIndex();
    }
}
