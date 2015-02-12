/*
 * Copyright 2014 Florian Vogelpohl <floriantobias@gmail.com>.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package dataAccess;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;


/**
 * This class encapsulates the EntityManagerFactory instance for the given
 * Persistence Unit. You can order a thread EntityManager and you get only one
 * EntityManager instance for your thread.
 *
 * The Jpa class stores the EntityManager instances as
 *
 * @author Florian Vogelpohl <floriantobias@gmail.com>
 */
public final class Jpa implements AutoCloseable {
    private final String puUnit;
    private EntityManagerFactory emf;
    private ThreadLocal<EntityManager> entityStorage;

    public Jpa(String persistenceUnit) {
        this.puUnit = persistenceUnit;
    }

    /**
     * Etablish the connection to the database and set ups the entitymanager
     * factory.
     */
    public void open() {
        if (emf == null || !emf.isOpen()) {
            emf = Persistence.createEntityManagerFactory(puUnit);
            entityStorage = new ThreadLocal<>();
        }
    }
    
    public boolean isEntityManagerFactoryNull(){
        return emf==null;
    }
    
    /**
     * Checks if the EntityManagerFactory is open.
     * 
     * @return true if open, otherwise false 
     */
    public Boolean isOpen() {
        return emf != null && emf.isOpen();
    }

    /**
     * Closes the entitymanager factory and all used entitymanagers
     */
    @Override
    public void close() {
        if (emf.isOpen()) {
            emf.close();

            entityStorage = null;
        }
    }

    /**
     * Returns an {@link EntityManager} which is stored by the called Thread.
     *
     * @return EntityManager instance
     */
    public EntityManager getThreadEntityManager() {
        EntityManager em = entityStorage.get();

        if (em == null || !em.isOpen()) {
            em = createEntityManager();
            entityStorage.set(em);
        }

        return em;
    }

    /**
     * Creates an {@link EntityManager}.
     *
     * @return EntityManager instance
     */
    public EntityManager createEntityManager() throws AssertionError{
        if (emf == null || !emf.isOpen()) {
            throw new AssertionError("EntityManagerFactory is not opened!");
        }

        return emf.createEntityManager();
    }
}
