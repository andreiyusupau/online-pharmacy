package com.vironit.onlinepharmacy.dao.jpa;

import com.vironit.onlinepharmacy.dao.RecipeDao;
import com.vironit.onlinepharmacy.model.Recipe;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Collection;
import java.util.Optional;

@Repository
public class JpaRecipeDao implements RecipeDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public long add(Recipe recipe) {
        entityManager.getTransaction()
                .begin();
        entityManager.persist(recipe);
        entityManager.getTransaction()
                .commit();
        return recipe.getId();
    }

    @Override
    public Optional<Recipe> get(long id) {
        Recipe recipe = entityManager.find(Recipe.class, id);
        entityManager.detach(recipe);
        return Optional.of(recipe);
    }

    @Override
    public Collection<Recipe> getAll() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Recipe> criteriaQuery = criteriaBuilder.createQuery(Recipe.class);
        Root<Recipe> root = criteriaQuery.from(Recipe.class);
        criteriaQuery.select(root);
        return entityManager.createQuery(criteriaQuery)
                .getResultList();
    }

    @Override
    public boolean remove(long id) {
        entityManager.getTransaction()
                .begin();
        Recipe recipe = entityManager.find(Recipe.class, id);
        entityManager.remove(recipe);
        entityManager.getTransaction()
                .commit();
        return true;
    }

    @Override
    public Optional<Recipe> getByOrderPositionId(long id) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Recipe> criteriaQuery = criteriaBuilder.createQuery(Recipe.class);
        Root<Recipe> root = criteriaQuery.from(Recipe.class);
        criteriaQuery.select(root)
                .where(criteriaBuilder.equal(root.get("orderPosition")
                        .get("id"), id));
        return Optional.of(entityManager.createQuery(criteriaQuery)
                .getSingleResult());
    }
}
