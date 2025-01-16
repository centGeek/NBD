package shop.orm.repository.mapper;

import com.datastax.oss.driver.api.mapper.annotations.DaoFactory;
import com.datastax.oss.driver.api.mapper.annotations.DaoKeyspace;
import com.datastax.oss.driver.api.mapper.annotations.DaoTable;
import com.datastax.oss.driver.api.mapper.annotations.Mapper;
import shop.orm.repository.dao.ProductDao;

@Mapper
public interface ProductMapper {

    @DaoFactory
    ProductDao productDao(@DaoKeyspace String keyspace, @DaoTable String table);

    @DaoFactory
    ProductDao productDao();
}

