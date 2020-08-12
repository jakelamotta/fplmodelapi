package com.fplstats.business.model;

import com.fplstats.common.dto.fplstats.LinearRegressionDto;

import java.util.List;

public class Utility {

    public static LinearRegressionDto getLinearRegressionCoefficents(List<Double> xValues, List<Double> yValues){

        if (xValues.size() != yValues.size())
        {
            throw new IllegalArgumentException("In linear regression y and x has to be equally lpong");
        }

        LinearRegressionDto result = new LinearRegressionDto();

        double y = 0.0;
        double x = 0.0;
        double xy = 0.0;
        double x2 = 0.0;
        double y2 = 0.0;
        int dataPoints = xValues.size();

        for (int i = 0; i < xValues.size(); i++)
        {
            double curX = xValues.get(i);
            double curY = yValues.get(i);
            x += curX;
            y += curY;

            xy += curX * curY;
            x2 += curX * curX;
            y2 += curY * curY;
        }

        result.setAlpha((y * x2 - x * xy) / (dataPoints * x2 - (x * x)));
        result.setBeta((dataPoints * xy - x * y) / (dataPoints*x2 -(x*x)));

        return result;
    }

}
