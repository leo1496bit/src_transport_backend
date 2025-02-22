package com.src.src_transport.model;

public class GooglePlaceDetailsResponse {
    private String status;
    private Result result;

    public String getStatus() {
        return status;
    }


    public Result getResult() {
        return result;
    }



    public static class Result {
        private Geometry geometry;

        public Geometry getGeometry() {
            return geometry;
        }


        public static class Geometry {
            private Location location;

            public Location getLocation() {
                return location;
            }


            public static class Location {
                private double lat;
                private double lng;

                public double getLat() {
                    return lat;
                }

                public double getLng() {
                    return lng;
                }

            }
        }
    }
}
