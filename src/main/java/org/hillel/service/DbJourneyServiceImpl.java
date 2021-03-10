package org.hillel.service;

import org.hillel.Journey;
import org.hillel.repository.DataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class DbJourneyServiceImpl implements JourneyService {

    private DataSource dataSource;

    public DbJourneyServiceImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Collection<Journey> find(String stationFrom, String stationTo, LocalDate dateFrom, LocalDate dateTo) throws Exception {
        Connection connection = null;
        List<Journey> journeys = new ArrayList<>();
        try {
            connection = dataSource.getConnection();
            try (PreparedStatement statement = connection.prepareStatement(
                    "select * from journey " +
                    "where station_from = ? and station_to = ? and departure >= ? and arrival <= ?")) {
                statement.setString(1, stationFrom);
                statement.setString(2, stationTo);
                statement.setObject(3, dateFrom);
                statement.setObject(4, dateTo);
                try (ResultSet rs = statement.executeQuery()) {
                    while (rs.next()) {
                        Journey journey =
                                new Journey(rs.getString("station_from"),
                                        rs.getString("station_to"),
                                        rs.getObject("departure", LocalDate.class),
                                        rs.getObject("arrival", LocalDate.class));
                        journeys.add(journey);
                    }
                }
            }
        } finally {
            dataSource.close(connection);
        }
        return journeys;
    }
}
